# polls/models.py
from django.db import models
from django.utils import timezone

class Poll(models.Model):
    title = models.CharField("Название опроса", max_length=200)
    description = models.TextField("Описание", blank=True)
    start_date = models.DateTimeField("Дата начала", default=timezone.now)
    is_active = models.BooleanField("Активен", default=True, help_text="Активные опросы видны пользователям")
    notified = models.BooleanField("Уведомление отправлено", default=False, editable=False)

    def __str__(self):
        return self.title
    class Meta:
        verbose_name = "Опрос"
        verbose_name_plural = "Опросы"

class Question(models.Model):
    poll = models.ForeignKey(Poll, on_delete=models.CASCADE, related_name='questions', verbose_name="Опрос")
    text = models.CharField("Текст вопроса", max_length=255)

    def __str__(self):
        return self.text
    class Meta:
        verbose_name = "Вопрос"
        verbose_name_plural = "Вопросы"

class Choice(models.Model):
    question = models.ForeignKey(Question, on_delete=models.CASCADE, related_name='choices', verbose_name="Вопрос")
    text = models.CharField("Вариант ответа", max_length=200)
    votes = models.IntegerField("Количество голосов", default=0)

    def __str__(self):
        return self.text
    class Meta:
        verbose_name = "Вариант ответа"
        verbose_name_plural = "Варианты ответа"