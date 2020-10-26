from django.shortcuts import render
from django.views import View
from django.views.decorators.csrf import csrf_exempt
from django.contrib.auth import authenticate
from django.contrib.auth.forms import UserCreationForm
from django.contrib.auth.forms import AuthenticationForm
from django.contrib.auth.forms import UserChangeForm
from django.http import JsonResponse

import jwt
from decouple import config
# Create your views here.

class AccountView(View):
    # 회원 생성
    def post(self, request):
        username = request.POST.get('username', "")
        password1 = request.POST.get('password1', "")
        password2 = request.POST.get('password2', "")
        form_data = {
            'username': username,
            'password1': password1,
            'password2': password2,
        }
        signup_form = UserCreationForm(form_data)
        if signup_form.is_valid():
            signup_form.save()
            data = {
                'result': 'success'
            }
            return JsonResponse(data)
        else:
            signup_form = UserCreationForm()
        data = {
            'result': signup_form.error_messages
        }
        return JsonResponse(data)

    # 회원 탈퇴
    def delete(self, request):
        username = request.GET.get("username", "")
        password = request.GET.get("password", "")
        user = authenticate(username=username, password=password)
        if user is not None:
            user.delete()
            data = {
                'result': "회원 탈퇴"
            }
        return JsonResponse(data)

@csrf_exempt
def login(request):
    if request.method == 'POST':
        username = request.POST.get('username', "")
        password = request.POST.get('password', "")
        form_data = {
            'username': username,
            'password': password,
        }
        form = AuthenticationForm(data=form_data)

        if form.is_valid():
            user = authenticate(username=username, password=password)
            if user is not None:
                token = jwt.encode({"username": username}, config(
                    'SECRET_KEY'), algorithm="HS256")
                token = token.decode("utf-8")
                data = {
                    "token": token,
                    "user_pk": user.pk
                }
                return JsonResponse(data)

            else:
                data = {}
                data['error'] = "존재하지 않는 회원입니다."
        
        else:
            data = {}
            data['error'] = "아이디, 비밀번호를 확인해주세요."
    
    return JsonResponse(data)