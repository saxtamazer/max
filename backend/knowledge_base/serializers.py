# knowledge_base/serializers.py
from rest_framework import serializers
from .models import Article, Category # <-- Импортируем Category

# --- ДОБАВЬТЕ СЕРИАЛИЗАТОР ДЛЯ КАТЕГОРИЙ ---
class CategorySerializer(serializers.ModelSerializer):
    class Meta:
        model = Category
        fields = ['id', 'name', 'slug']
# --- КОНЕЦ ДОБАВЛЕНИЯ ---

class ArticleListSerializer(serializers.ModelSerializer):
    # Урезанный сериализатор для списка статей
    # --- ДОБАВЛЯЕМ НОВЫЕ ПОЛЯ ---
    category_name = serializers.CharField(source='category.name', read_only=True, default=None)
    main_image = serializers.ImageField(read_only=True)
    # --- КОНЕЦ ДОБАВЛЕНИЯ ---

    class Meta:
        model = Article
        # Обновляем список полей
        fields = ['id', 'title', 'created_at', 'main_image', 'category_name']


class ArticleDetailSerializer(serializers.ModelSerializer):
    # Полный сериализатор для одной статьи
    # --- ДОБАВЛЯЕМ ПОЛЕ КАТЕГОРИИ ---
    category_name = serializers.CharField(source='category.name', read_only=True, default=None)
    # --- КОНЕЦ ДОБАВЛЕНИЯ ---
    
    class Meta:
        model = Article
        fields = '__all__'
        # Если хотите быть более явными, перечислите поля:
        # fields = ['id', 'title', 'content', 'created_at', 'updated_at', 
        #           'main_image', 'category', 'category_name']