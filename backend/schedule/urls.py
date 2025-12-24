# schedule/urls.py
from django.urls import path
from . import views

urlpatterns = [
    # Публичный URL для всех
    path('view/', views.schedule_for_all),
    # Приватный URL для старост
    path('manage/', views.manage_schedule_for_headman),
]