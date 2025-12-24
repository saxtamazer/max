# /srv/max-mini-app/backend/schedule/serializers.py
from rest_framework import serializers
from .models import ScheduleEvent

class ScheduleEventSerializer(serializers.ModelSerializer):
    class Meta:
        model = ScheduleEvent
        # --- ИЗМЕНЕНИЕ: Добавляем 'week_type' в список полей ---
        fields = [
            'id', 
            'group', 
            'subject', 
            'teacher', 
            'room', 
            'start_time', 
            'end_time', 
            'week_type'  # <-- ДОБАВЛЕНО ЭТО ПОЛЕ
        ]