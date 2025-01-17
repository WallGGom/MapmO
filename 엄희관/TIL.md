# TIL

# :spiral_calendar: 1019(월)

Android Studio 설치

문제발생) 

* 커스텀설정 없이 그대로 설치하였을 때 블루 스크린(재부팅) 문제 발생

해결과정) 

* SDK와 Glide 삭제 후 재설치 - But, 왜 이러한 현상이 생기는지 이해X



문제발생) 

* Virtual Device 생성 후 실행시켰을 때 내가 만든 App 화면이 보이지 않음

* 실행을 시켰을 때 실행버튼이 그대로 활성화 되어 유지...
* 다른 Virtual Device로 실행했을 때 문제가 발생한다...



### :bulb: 코틀린의 기본 타입
1. 실수형
* var doubleValue:Double = 3.14
2. 정수형
* var IntValue:Int = 123456789
* var longValue:Long = 32_147_483_647
3. 문자형

4. 불린형



변수에 대해서 초기화를 시켜야 한다.(JAVA와 비슷한 언어)

초기화를 시킬 때 val, var을 사용

val

* immutable(재할당 불가능)

var

* mutable(재할당 가능)

참고 : https://velog.io/@jojo_devstory/Kotlin-val-var%EC%9D%98-%EC%B0%A8%EC%9D%B4%EC%A0%90



# :spiral_calendar: 1020(화)

## :bulb: 라이브 강의

#### 레벨 1

* JWT
* TypeScript
* Docker
* nginx

#### 레벨 2

* JPA
* React
* mongoDB
* websocket
* Redis
* Jenkins
* Spring Batch

어떠한 기술인지 어떤 상황에서 사용해야하는지에 대한 이해가 필요하다



정리 : Mockup 제작 및 아이디어 구체화...한다고 1일 1커밋 깜빡했는데 잊지말고 하자!



# :spiral_calendar: 1021(수)

SDK Platforms - Android 10.0(Q)



Android Studio에서 되도록 낮은 사양의 에뮬레이터에서 테스트하는 것이 좋다.

* Android Studio 성능에 영향을 덜 미치기 때문



Q) 안드로이드 스튜디오 디렉터리 구조에서 이미지와 같은 리소스를 저장하는 디렉토리 이름

A) res

Q) 내가 만드는 앱의 빌드 정보가 들어 있는 파일 이름은?

A) build.gradle

Q) 에뮬레이터의 속도를 빠르게 해주는 하드웨어 가속기의 이름은?

A) HAXM(Emulator Accelerator)



### 로그

> 로그는 동작에 대한 기록. Android Studio에서 말하는 Log는 안드로이드 라이브러리로, 로그 안에 미리 정의되어 있는 함수들을 호출해서 사용하는 것

로그 클래스에서 일반적으로 사용되는 함수

* v (verbose) : 상세한 로그 내용을 출력
* i (information) : 정보성의 일반적인 메세지를 전달
* d (debug) : 개발에 필요한 내용을 출력(개발자용)
* w (warning) : 에러가 아닌 경고성 메세지를 전달
* e (error) : 에러 메세지를 출력

```kotlin
Log.d("태그", "출력 메시지");
```

d는 debug를 의미



### 변수와 상수

#### 2.1 변수 var

> var 변수명(이름) = 값

```kotlin
var myName = "홍길동"
```

> var 변수명: 타입
>
> 변수명 = 값

```kotlin
var myAge: Int
myAge = 27
```



#### 2.2 데이터 타입

숫자형

* Double : 소수점이 있는 값

* Float : 소수점이 있는 값 + F 

  * ```kotlin
    var floatValue: Float
    floatValue = 3.141592F
    ```

* Long

  * ``` kotlin
    var longValue: Long
    longValue = 2147483647L
    ```

* Int

  * ```kotlin
    var intValue: Int
    intValue = 2147483647
    
    // 언더바로 자릿수 구분
    intValue = 2_147_483_647
    ```

* Short

  * ```kotlin
    var shortValue: Short = 32_767
    var byteValue: Byte = 127
    ```

* Byte

문자형

* Char

  * ```kotlin
    var charValue = 'A'
    ```

* String

  * ```kotlin
    var stringValue: String = "ABCDEF"
    ```

불린형

* Boolean

  * ```kotlin
    var boolValue = true
    ```



### 2.3 상수 val

> immutable / 한 번 입력된 값은 변경X

#### 상수 선언하기

> val 상수 이름 = 값

```kotlin
val PI = 3.141592F
val myRoomArea = 10 * 10 * PI
```



### 2.4 네이밍 컨벤션

#### 클래스명

* Camel Case
* 단어마다 대문자

```kotlin
class MainActivity
```

#### 함수명과 변수명

* 첫글자는 소문자 이후 새로운 단어의 첫 글자는 대문자

```kotlin
fun onCreateActivity()

var inValue: Int
```

#### 상수명

* 모두 대문자로 작성

```kotlin
val HELLO: String = "안녕"
```

* 상수명이 2개 이상의 단어로 이루어져 있다면 스네이크 케이스(Snake Case)를 사용

```kotlin
val HOW_ARE_YOU: String = "어떻게 지내?"
```



### 조건문

> JavaScript와 사용법이 같다.

* if
* if ~ else
* if ~ else if ~ else

#### 변수에 직접 if문 사용하기

```kotlin
var a = 5
var b - 3
var bigger = if (a > b) a else b
```

#### if문의 마지막 값을 반환값으로 사용

```kotlin
var a = 5
var b = 3
var bigger = if (a > b) {
    a = a - b
    a // 마지막 줄의 a 값이 변수 bigger에 저장
}
```



### 3.2 조건문 when

> switch문과 유사

```kotlin
switch (변수) {
    case 비교값 : 
    	// 변수값이 비교값과 같다면 이 영역이 실행
}
```

#### 일반적인 형태의 when 사용하기

```kotlin
when (파라미터) {
    비교값 -> {
        // 변수값이 비교값과 같다면 이 영역이 실행
    }
}
```

switch 문과 비교하면 값을 비교하는 줄 앞의 casr가 없어지고

비교값 다음의 콜론(:)이 화살표 연산자로 대체

여러개의 값을 하나의 when문에서 비교할 수도 있다.

```kotlin
when (파라미터) {
    비교값1 -> {
        // 변수의 값 = 비교값1
    }
    비교값2 -> {
        // 변수의 값 = 비교값2
    }
    else -> {
        // 변수의 값이 앞에서 비교한 값들과 다를 경우 실행
    }
}
```

#### 콤마로 구분해서 사용

특정 값을 비교하는데 결과 처리가 동일하면 콤마(,)로 구분해서 비교

```kotlin
var now = 9
when (now) {
    8, 9 -> {
        Log.d("When", "현재 시간은 8시 또는 9시입니다.")
    }
    else -> {
        Log.d("when", "현재 시간은 9시가 아닙니다.")
    }
}
```

#### 범위 값을 비교하기

in을 사용해서 범위값을 비교 할 수 있다.

if문의 비교 연산자 중 <=. =>과 같은 기능을 구현할 수 있다.

```kotlin
var ageOfMichael = 19
when (ageOfMichael) {
    in 10..19 -> {
        Log.d("when", "마이클은 10대입니다.")
    }
    !in 10..19 -> {
        Log.d("when", "마이클은 10대가 아닙니다.")
    }
    else {
        Log.d("when", "마이클의 나이를 알 수 없습니다.")
    }
}
```

#### 파라미터 없는 when 사용

when 다음에 오는 괄호를 생략하고 if문처럼 사용 가능

```kotlin
var currentTime = 6
when {
    currentTime == 5 -> {
        Log.d("when", "현재 시간은 5시 입니다.")
    }
    currentTime > 5 -> {
        Log.d("when", "현재 시간은 5시가 넘었습니다.")
    }
    else {
        Log.d("when", "현재 시간ㅇ느 5시 이전입니다.")
    }
}
```



### 배열과 컬렉션

배열(Array) / 컬렉션(Collection)

### 4.1 배열

먼저 개수를 정해놓고 사용하며 중간에 개수를 추가하거나 제거할 수 없다.

> var 변수 = Array(개수)

```kotlin
var students = IntArray(10)
var longArray = LongArray(10)
var CharArray = CharArray(10)
var FloatArray = FloatArray(10)
var DoubleArray = DoubleArray(10)
```

#### 문자 배열에 빈 공간 할당

```kotlin
var stringArray = Array(10, {item->""})
```

#### 값으로 배열 공간 할당

arrayOf() 함수를 사용해서 String 값을 직접 할당

```kotlin
var dayArray = arrayOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
```

#### 배열에 값 입력

> 배열명[인덱스] = 값
>
> 배열명.set(인덱스, 값)

#### 배열에 있는 값 꺼내기

> 배열명[인덱스]
>
> 배열명.get(인덱스)

```kotlin
var seventhValue = intArray[6]
var tenthValue = intArray.get(9)
```

### 4.2 컬렉션

#### 리스트(List)

* 중복된 값 입력 가능
* 뮤터블(Mutable)이라는 접두어(prefix)가 붙는다.

```kotlin
var list = mutableListOf("MON", "TUE", "WED")
```

#### - 리스트 생성하기 : mutableListOf

#### - 리스트에 값 추가하기 : add

#### - 리스트에 입력된 값 사용하기 : get

#### - 리스트값 수정하기 : set

```kotlin
mutableList.set(1, "수정할 값")
```

#### - 리스트에 입력된 값 제거하기 : removeAt

> 인덱스를 지정

```kotlin
mutableList.removeAt(1)
```

#### - 빈 리스트 사용하기

> var 변수명 = mutableListOf<컬렉션에 입력될 값의 타입>()

```kotlin
var stringList = mutableListOf<String>()
```

#### - 컬렉션 개수 가져오기 : size

```kotlin
mutableList.size
```



### 셋(set)

> 중복을 허용하지 않는 리스트

인덱스로 조회할 수 없다.

get 함수 지원X

```kotlin
var set = mutableSetOf<String>()
set = mutableSetOf<String>()
set.add("JAN")
set.add("FEB")
set.add("MAR")

set.remove("FEB")
```



### 맵(Map)

#### 맵 생성하기

키(key)와 값(value)의 쌍으로 입력되는 컬렉션

```kotlin
var map = mutableMapOf<String, String>()
map.put("키1", "값1")
map.put("키2", "값2")
map.put("키3", "값3")

Log.d("CollectionMap", "map에 입력된 키1의 값은 ${map.get("키1")}입니다.")
```

#### 맵 수정하기

```kotlin
map.put("키2", "수정")
```

#### 맵 삭제하기

```kotlin
map.remove("키2")
```

---

### 반복문

### 5.1 for 반복문

#### for in .. : 일반적인 형태의 for 반복문

```kotlin
for (변수 in 시작값..종료값) {
    // 실행코드
}

for (index in 1..10) {
    Log.d("For", "현재 숫자는 ${index}")
}
```

#### until : 마지막 숫자 제외하기

```kotlin
for (변수 in 시작값 until 종료값) {
    // 실행코드
}

var array = arrayOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN")
for (index in 0 until array.size) {
    Log.d("For", "현재 월은 ${array.get(index)}입니다.")
}
```

#### step : 건너뛰기

```kotlin
for (변수 in 시작값..종료값 step 3) {
    // 실행코드
}

for (index in 0..100 step 3) {
    Log.d("For", "현재 숫자는 ${index}")
}
```

#### downTo: 감소시키기

```kotlin
for (index in 0 downTo 10) {
    Log.d("For", "현재 숫자는 ${index}")
}
```

#### 배열, 컬렉션에 들어 있는 엘리먼트 반복하기

```kotlin
for (변수 in 배열 또는 컬렉션) {
    // 실행 코드
}

var arrayMonth = arrayOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN")
for (month in arrayMonth) {
    Log.d("For", "현재 월은 ${month}입니다.")
}
```



### 5.2 while 반복문







---

### 함수



---

![image-20201021233538227](C:\Users\multicampus\AppData\Roaming\Typora\typora-user-images\image-20201021233538227.png)

GPS가 꼭 켜져 있지 않더라도 네트워크를 통해서 위치를 가져올 수 있다.(단, 정확도는 감소)



# :spiral_calendar: 1022(목)

오늘 한 일 : 발표준비 / Map 출력



# :spiral_calendar: 1023(금)

```kotlin
    // -- 내 위치를 가져오는 코드
    var fusedLocationClient: FusedLocationProviderClient 
	// network + GPS : 아주빠른 주기 동안 GPS를 가져오면 배터리소모가 커진다.
	// 그렇다고 GPS를 늦게 가져오면?? 정확성이 떨어진다.
	// 따라서 network와 GPS를 적절하게하여 사용하여 자체적으로 배터리 소모를 줄여주도록 도와주는 친구

    var locationCallback: LocationCallback 
	// 위의 함수로 가져온 GPS를 콜백해주는 함수
```



# :spiral_calendar: 1025(일)

### Text

언더바가 있으면 모두 에디트 텍스트(EditText) 위젯

언더바가 없으면 텍스트뷰



### Buttons

클릭 이벤트를 받는 위젯이기 때문에 버튼을 터치한 후에 손가락을 움직여 다른 위치에서 떼면 동작하지 않는다.(물론 의도적으로 터치 이벤트에 동작하도록 설계되 버튼도 있다.)

### Widgets

이미지, 웹 사이트, 별점표시, 진행상태 등의 정보를 화면에 그리는 위젯 모음



> import 문제

```kotlin
import kotlinx.android.synthetic.main.activity_main.*
```

![image-20201025183130016](C:\Users\multicampus\AppData\Roaming\Typora\typora-user-images\image-20201025183130016.png)



# :spiral_calendar: 1028(수)

코틀린에서는 화면전환하는 방법이 다양하다... (like Vue의 컴포넌트)



데이터 이동없이 화면만 전환하고 싶으면 - Fragment

화면 전환과 함께 데이터 이동을 할 경우(ex 모달 등) - Activity

* 데이터는 Intent를 이용하여 보낼 수 있다.



# :spiral_calendar: 1029(목)

지도 API - 구글 API의 경우 Key마다 연결짓는 패키지명이 있다. 해당 패키지명이 프로젝트 명과 일치하지 않으면 오류가 난다.



모든 IDE에서(Web개발 시 Console창) Terminal(또는 Run)에서 오류를 발견했듯이 Android Studio에서도 Run을 꼭 클릭하여 오류를 찾자. - Syntax Error를 제외한 오류 확인 가능



Activity와 Layout에 대한 이해가 부족한 것 같다. 컨설턴트님이 말씀해주신 것 처럼 새로운 언어에 대한 문법은 금방 이해할 수 있다. 중요한 것은 그 언어가 가지고 있는 성격과 특징이다.(ex 생애주기 등)



# :spiral_calendar: 1030(금)

DAO란?

Data Access Object의 약어로 데이터베이스에 접근해서 DML 쿼리(SELECT, INSERT, UPDATE, DELETE)를 실행하는 메서드의 모음입니다.

```kotlin
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/btnCreate"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="282dp"
        android:layout_marginLeft="282dp"
        android:layout_marginTop="30dp"
        android:layout_marginEnd="37dp"
        android:layout_marginRight="37dp"
        android:text="메모 생성"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <androidx.viewpager2.widget.ViewPager2
        android:id="@+id/pager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior"
        />

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_margin="@dimen/fab_margin"
        app:srcCompat="@android:drawable/ic_dialog_email" />

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab2"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|start"
        android:layout_margin="@dimen/fab_margin"
        app:layout_constraintBottom_toTopOf="@+id/pager"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:srcCompat="@android:drawable/ic_menu_rotate" />
</androidx.constraintlayout.widget.ConstraintLayout>
```



# :spiral_calendar: 1104(수)

View는 layout과 widget으로 나뉜다.

R은 res 폴더를 의미

this는 해당 Activity에서의 context를 사용할 경우 넣어주면 된다.



### ListView

![image-20201104092116120](C:\Users\multicampus\AppData\Roaming\Typora\typora-user-images\image-20201104092116120.png)



ItemClick : 항목을 터치하면 발생

![image-20201104092128120](C:\Users\multicampus\AppData\Roaming\Typora\typora-user-images\image-20201104092128120.png) 

 

![image-20201104092208083](C:\Users\multicampus\AppData\Roaming\Typora\typora-user-images\image-20201104092208083.png)

### RecyclerView

![image-20201104092358105](C:\Users\multicampus\AppData\Roaming\Typora\typora-user-images\image-20201104092358105.png)



![image-20201104092423867](C:\Users\multicampus\AppData\Roaming\Typora\typora-user-images\image-20201104092423867.png)

![image-20201104092509183](C:\Users\multicampus\AppData\Roaming\Typora\typora-user-images\image-20201104092509183.png)



메모 일간으로 출력할 때 

![image-20201104093149785](C:\Users\multicampus\AppData\Roaming\Typora\typora-user-images\image-20201104093149785.png)



## :memo: 오늘 진행한 내용

날짜 / 시간 MainActivity로 넘기기(Room 사용)

spinner 사용

* spinner는 Dialog 모드로 생성
* spinner에서 선택시 selectAlarmsettime 이벤트 발생



# :spiral_calendar: 1106(금)

From PagerRecyclerAdapter To Activity 

* 참고 : https://kumgo1d.tistory.com/44





