# api/urls.py
from django.urls import path, include

urlpatterns = [
    path('kb/', include('knowledge_base.urls')),
    path('polls/', include('polls.urls')),
    path('schedule/', include('schedule.urls')), # Добавляем эту строку
]