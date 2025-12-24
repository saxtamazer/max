# knowledge_base/urls.py
from django.urls import path
from . import views

urlpatterns = [
    path('articles/', views.article_list),
    path('articles/<int:pk>/', views.article_detail),
    path('categories/', views.category_list), # <-- ДОБАВЬТЕ ЭТУ СТРОКУ
]