# /srv/max-mini-app/backend/backend_core/urls.py

from django.contrib import admin
from django.urls import path, include
# --- ДОБАВЬТЕ ЭТИ ИМПОРТЫ ---
from django.conf import settings
from django.conf.urls.static import static
# --- КОНЕЦ ДОБАВЛЕНИЯ ---


urlpatterns = [
    path('admin/', admin.site.urls),
    path('api/v1/', include('api.urls')),
    path('tinymce/', include('tinymce.urls')),
]

# --- ДОБАВЬТЕ ЭТО УСЛОВИЕ В КОНЕЦ ФАЙЛА ---
if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)
# --- КОНЕЦ ДОБАВЛЕНИЯ ---