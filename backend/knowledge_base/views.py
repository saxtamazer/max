# knowledge_base/views.py

from rest_framework.decorators import api_view
from rest_framework.response import Response
from .models import Article, Category # <-- Импортируем Category
from .serializers import ArticleListSerializer, ArticleDetailSerializer, CategorySerializer # <-- Импортируем CategorySerializer
from django.db.models import Q # <-- Импортируем Q для сложного поиска


@api_view(['GET'])
def article_list(request):
    """
    Возвращает список статей.
    Поддерживает фильтрацию по ?category=slug и поиск по ?search=text.
    """
    queryset = Article.objects.select_related('category').all()

    # Поиск
    search_query = request.query_params.get('search', None)
    if search_query:
        queryset = queryset.filter(
            Q(title__icontains=search_query) | 
            Q(content__icontains=search_query)
        )

    # Фильтрация по категории
    category_slug = request.query_params.get('category', None)
    if category_slug:
        queryset = queryset.filter(category__slug=category_slug)
    
    serializer = ArticleListSerializer(queryset, many=True)
    return Response(serializer.data)


# --- ДОБАВЬТЕ НОВЫЙ VIEW ДЛЯ КАТЕГОРИЙ ---
@api_view(['GET'])
def category_list(request):
    """Возвращает список всех категорий."""
    categories = Category.objects.all()
    serializer = CategorySerializer(categories, many=True)
    return Response(serializer.data)
# --- КОНЕЦ ДОБАВЛЕНИЯ ---


@api_view(['GET'])
def article_detail(request, pk):
    """Возвращает одну статью по ее ID."""
    try:
        # Используем select_related для оптимизации
        article = Article.objects.select_related('category').get(pk=pk)
        serializer = ArticleDetailSerializer(article)
        return Response(serializer.data)
    except Article.DoesNotExist:
        return Response(status=404)