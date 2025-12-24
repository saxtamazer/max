from django.urls import path
from . import views

urlpatterns = [
    path('', views.active_polls_list),
    path('choices/<int:choice_id>/vote/', views.vote),
    path('notify/', views.polls_for_notification),
]