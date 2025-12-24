# schedule/views.py
from rest_framework.decorators import api_view, permission_classes
from rest_framework.permissions import IsAuthenticated
from rest_framework.response import Response
from .models import ScheduleEvent
from .serializers import ScheduleEventSerializer
import datetime

@api_view(['GET'])
def schedule_for_all(request):
    """
    Отдает ВСЕ расписание на текущую неделю (четные, нечетные, обе).
    Дополнительно возвращает флаг, является ли текущая неделя четной.
    """
    today = datetime.date.today()
    
    # Вычисляем начало и конец текущей недели (Пн-Вс)
    start_of_week = today - datetime.timedelta(days=today.weekday())
    end_of_week = start_of_week + datetime.timedelta(days=6)
    
    # Определяем четность текущей недели
    is_even_week = today.isocalendar()[1] % 2 == 0
        
    # --- ГЛАВНОЕ ИЗМЕНЕНИЕ ---
    # Получаем ВСЕ события в диапазоне дат текущей недели,
    # у которых есть тип недели (т.е. они не разовые)
    events = ScheduleEvent.objects.filter(
        start_time__date__range=[start_of_week, end_of_week],
        week_type__in=['EVEN', 'ODD', 'BOTH']
    ).order_by('start_time')
    
    serializer = ScheduleEventSerializer(events, many=True)
    
    # Формируем новый формат ответа
    data = {
        'events': serializer.data,
        'current_week_is_even': is_even_week
    }
    
    return Response(data)

@api_view(['GET', 'POST'])
@permission_classes([IsAuthenticated]) # Этот view требует аутентификации
def manage_schedule_for_headman(request):
    """API для старост: просмотр и обновление расписания своей группы."""
    
    # Проверяем, является ли пользователь старостой и есть ли у него группа
    if not hasattr(request.user, 'headman_of_group'):
        return Response({"error": "You are not a headman of any group."}, status=403)
        
    group = request.user.headman_of_group

    if request.method == 'GET':
        events = ScheduleEvent.objects.filter(group=group)
        serializer = ScheduleEventSerializer(events, many=True)
        return Response(serializer.data)

    if request.method == 'POST':
        # TODO: Реализовать логику импорта из CSV
        # 1. Принять файл из request.FILES
        # 2. Удалить старое расписание для этой группы
        # 3. Пройти по строкам CSV и создать новые объекты ScheduleEvent
        return Response({"message": "CSV uploaded successfully (logic not implemented yet)."})