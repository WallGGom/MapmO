### Commit Message Convention

* jira 이슈 번호 | 커밋할 내용 | 이름

```bash
$ git commit -m "S03P31D203-9 | convention 정하기 | 강해솔"
```



### Kotlin Convention

* .kt 파일명 - PascalCase
  * ```MainActivity.kt```
* package명 - 소문자 권장/ 여러단어일 경우 camelCase

* 클레스명, Object명 - PascalCase

  ```kotlin
  class MyClass {}
  object EmptyDeclarationProcessor : DeclarationProcessor() {}
  ```

  

* 함수명 - camelCase

  ```kotlin
  fun sendMessage() {}
  ```



* 일반 변수 (상수 아닌 이름) - camelCase

  ```kotlin
  val notEmptyArray = arrayOf("these", "can", "change")
  ```

  

* 변경하지 않는 상수 - UPPER_SNAKE_CASE

  ```kotlin
  val USER_NAME_FIELD = "username"
  const val MAX_LIMIT = 8 
  ```

  

* .xml 파일명 - snake_case

  * ```[what]_[where].xml```

  * 예시

    ```activity_main.xml```

    ```fragment_article.xml```

    

* .xml 의 Ids - snake_case

  * ```[what]_[where]_[description]```

  * 예시

    ```tab_main```

    ```imageview_menu_profile```

    

* 이미지 (Drawables) - snake_case

  * ```[where]_[description]_[size]```

  * 여러 페이지의 공통으로 사용될 경우 where = all, common

  * 예시

    ```settings_icon_24dp```

    ```common_user_profile_32dp```

    

* 참고
  * 공식 문서 참고
    * https://developer.android.com/kotlin/style-guide?hl=ko
  * 블로그 참고
    * https://eosr14.tistory.com/71
    * https://b.jy.is/android-resource-naming-rule/