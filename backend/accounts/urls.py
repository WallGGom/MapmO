from django.urls import path
from . import views
from django.views.decorators.csrf import csrf_exempt

app_name = 'accounts'

urlpatterns = [
    path('account/', csrf_exempt(views.AccountView.as_view()), name='account'),
    path('login/', views.login, name='login'),
]