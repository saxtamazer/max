# polls/admin.py
from django.contrib import admin
from .models import Poll, Question, Choice

class ChoiceInline(admin.TabularInline):
    model = Choice
    extra = 2 # По умолчанию показывать 2 пустых варианта ответа

class QuestionInline(admin.StackedInline):
    model = Question
    inlines = [ChoiceInline]
    extra = 1 # По умолчанию показывать 1 пустой вопрос

@admin.register(Poll)
class PollAdmin(admin.ModelAdmin):
    list_display = ('title', 'start_date', 'is_active', 'notified')
    inlines = [QuestionInline]
    # TODO: Добавить действие для отправки уведомления