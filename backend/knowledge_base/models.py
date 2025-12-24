# knowledge_base/models.py

from django.db import models
from tinymce.models import HTMLField

# --- ДОБАВЬТЕ НОВУЮ МОДЕЛЬ КАТЕГОРИИ ---
class Category(models.Model):
    name = models.CharField(max_length=100, unique=True, verbose_name="Название")
    slug = models.SlugField(max_length=100, unique=True, verbose_name="URL-слаг")

    def __str__(self):
        return self.name

    class Meta:
        verbose_name = "Категория"
        verbose_name_plural = "Категории"
        ordering = ['name']
# --- КОНЕЦ ДОБАВЛЕНИЯ ---


class Article(models.Model):
    title = models.CharField(max_length=255, verbose_name="Заголовок")
    
    # --- ДОБАВЬТЕ ЭТИ ПОЛЯ ---
    category = models.ForeignKey(
        Category, 
        on_delete=models.SET_NULL, 
        null=True, 
        blank=True, 
        verbose_name="Категория"
    )
    main_image = models.ImageField(
        upload_to='article_covers/', 
        blank=True, 
        null=True, 
        verbose_name="Главное изображение"
    )
    # --- КОНЕЦ ДОБАВЛЕНИЯ ---

    content = HTMLField(verbose_name="Содержимое")
    created_at = models.DateTimeField(auto_now_add=True, verbose_name="Дата создания")
    updated_at = models.DateTimeField(auto_now=True, verbose_name="Дата обновления")

    def __str__(self):
        return self.title

    class Meta:
        verbose_name = "Статья"
        verbose_name_plural = "Статьи"
        ordering = ['-created_at']