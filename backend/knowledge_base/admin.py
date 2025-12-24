# knowledge_base/admin.py

from django.contrib import admin
from .models import Article, Category # <-- Импортируем Category

# --- ДОБАВЬТЕ АДМИН-КЛАСС ДЛЯ КАТЕГОРИЙ ---
@admin.register(Category)
class CategoryAdmin(admin.ModelAdmin):
    list_display = ('name', 'slug')
    prepopulated_fields = {'slug': ('name',)} # Автозаполнение слага
# --- КОНЕЦ ДОБАВЛЕНИЯ ---


@admin.register(Article)
class ArticleAdmin(admin.ModelAdmin):
    # Обновляем отображение и добавляем фильтры
    list_display = ('title', 'category', 'created_at', 'updated_at')
    list_filter = ('category', 'created_at') # <-- Добавляем фильтр по категории
    search_fields = ('title', 'content')