# schedule/models.py
from django.db import models
from django.conf import settings # Для связи с моделью User

class StudyGroup(models.Model):
    name = models.CharField("Название группы", max_length=100, unique=True)
    # Связываем старосту с группой. Один староста - одна группа.
    headman = models.OneToOneField(
        settings.AUTH_USER_MODEL,
        on_delete=models.SET_NULL,
        null=True, blank=True,
        related_name="headman_of_group",
        verbose_name="Староста"
    )
    def __str__(self): return self.name
    class Meta:
        verbose_name = "Учебная группа"
        verbose_name_plural = "Учебные группы"

class ScheduleEvent(models.Model):
    class WeekType(models.TextChoices):
        EVEN = 'EVEN', 'Четная'
        ODD = 'ODD', 'Нечетная'
        BOTH = 'BOTH', 'Еженедельно'

    group = models.ForeignKey(StudyGroup, on_delete=models.CASCADE, related_name="events", verbose_name="Группа")
    subject = models.CharField("Название предмета", max_length=200)
    teacher = models.CharField("Преподаватель", max_length=200, blank=True)
    room = models.CharField("Аудитория", max_length=50, blank=True)
    start_time = models.DateTimeField("Время начала")
    end_time = models.DateTimeField("Время окончания")
    week_type = models.CharField(
        "Тип недели",
        max_length=4,
        choices=WeekType.choices,
        default=WeekType.BOTH
    )

    def __str__(self):
        return f"{self.subject} ({self.group.name})"
    class Meta:
        verbose_name = "Занятие в расписании"
        verbose_name_plural = "Расписание"
        ordering = ['start_time']