from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework import status
from .models import Poll, Choice
from .serializers import PollSerializer

@api_view(['GET'])
def active_polls_list(request):
    """Возвращает список активных опросов."""
    polls = Poll.objects.filter(is_active=True)
    serializer = PollSerializer(polls, many=True)
    return Response(serializer.data)

@api_view(['POST'])
def vote(request, choice_id):
    """Принимает голос за конкретный вариант ответа."""
    try:
        choice = Choice.objects.get(pk=choice_id)
        # TODO: Добавить проверку, чтобы один пользователь не голосовал дважды
        choice.votes += 1
        choice.save()
        # Возвращаем обновленные данные всего опроса
        serializer = PollSerializer(choice.question.poll)
        return Response(serializer.data)
    except Choice.DoesNotExist:
        return Response({"error": "Choice not found"}, status=status.HTTP_404_NOT_FOUND)

@api_view(['GET', 'POST'])
def polls_for_notification(request):
    if request.method == 'GET':
        """Возвращает опросы, которые нужно анонсировать."""
        polls = Poll.objects.filter(is_active=True, notified=False)
        # Отдаем урезанные данные
        data = [{'id': poll.id, 'title': poll.title} for poll in polls]
        return Response(data)
    
    if request.method == 'POST':
        """Отмечает опрос как анонсированный."""
        poll_id = request.data.get('id')
        Poll.objects.filter(pk=poll_id).update(notified=True)
        return Response(status=status.HTTP_200_OK)