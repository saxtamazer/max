# schedule/admin.py
from django.contrib import admin
from import_export.admin import ImportExportModelAdmin
from .models import ScheduleEvent, StudyGroup

@admin.register(ScheduleEvent)
class ScheduleEventAdmin(ImportExportModelAdmin):
    list_display = ('subject', 'group', 'start_time', 'end_time', 'teacher', 'room')
    list_filter = ('group', 'start_time', 'teacher')

@admin.register(StudyGroup)
class StudyGroupAdmin(ImportExportModelAdmin):
    list_display = ('name',)