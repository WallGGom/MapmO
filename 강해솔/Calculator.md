### Calculator

MainActivity.kt

```kotlin
package com.example.spinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "테이블 레이아웃 계산기"

        btn0.setOnClickListener {
            if (Edit1.isFocused) {
                val num = Edit1.text.toString()
                if (!num.equals("0")) {
                    Edit1.setText(num + "0")
                }
            } else if (Edit2.isFocused) {
                val num = Edit2.text.toString()
                if (!num.equals("0")) {
                    Edit2.setText(num + "0")
                }
            }
        }
        btn1.setOnClickListener {
            if (Edit1.isFocused) {
                val num = Edit1.text.toString()
                if (!num.equals("0")) {
                    Edit1.setText(num + "1")
                } else {
                    Edit1.setText("1")
                }
            } else if (Edit2.isFocused) {
                val num = Edit2.text.toString()
                if (!num.equals("0")) {
                    Edit2.setText(num + "1")
                } else {
                    Edit2.setText("1")
                }
            }
        }

    }
}
```



```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <LinearLayout
        android:id="@+id/linearLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:layout_editor_absoluteX="-68dp"
        tools:layout_editor_absoluteY="33dp">
        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="125dp"
            android:orientation="vertical">


            <EditText
                android:id="@+id/Edit1"
                android:layout_width="match_parent"
                android:layout_height="56dp"
                android:text=""
                android:textSize="15sp"
                />

            <EditText
                android:id="@+id/Edit2"
                android:layout_width="match_parent"
                android:layout_height="56dp"
                android:text=""
                android:textSize="15sp"
                />

        </LinearLayout>

        <TableLayout
            android:layout_width="414dp"
            android:layout_height="127dp">
            <TableRow
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_margin="5dp">

                <Button
                    android:id="@+id/btn0"
                    android:layout_width="80dp"
                    android:layout_height="wrap_content"
                    android:textSize="20sp"
                    android:text="0">
                </Button>

                <Button
                    android:id="@+id/btn1"
                    android:layout_width="80dp"
                    android:layout_height="wrap_content"
                    android:textSize="20sp"
                    android:text="1">
                </Button>

                <Button
                    android:id="@+id/btn2"
                    android:layout_width="80dp"
                    android:layout_height="wrap_content"
                    android:textSize="20sp"
                    android:text="2">
                </Button>

                <Button
                    android:id="@+id/btn3"
                    android:layout_width="80dp"
                    android:layout_height="wrap_content"
                    android:textSize="20sp"
                    android:text="3">
                </Button>

                <Button
                    android:id="@+id/btn4"
                    android:layout_width="80dp"
                    android:layout_height="wrap_content"
                    android:textSize="20sp"
                    android:text="4">
                </Button>
            </TableRow>

            <TableRow
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_margin="5dp">

                <Button
                    android:id="@+id/btn5"
                    android:layout_width="80dp"
                    android:layout_height="wrap_content"
                    android:textSize="20sp"
                    android:text="5">
                </Button>

                <Button
                    android:id="@+id/btn6"
                    android:layout_width="80dp"
                    android:layout_height="wrap_content"
                    android:textSize="20sp"
                    android:text="6">
                </Button>

                <Button
                    android:id="@+id/btn7"
                    android:layout_width="80dp"
                    android:layout_height="wrap_content"
                    android:textSize="20sp"
                    android:text="7">
                </Button>

                <Button
                    android:id="@+id/btn8"
                    android:layout_width="80dp"
                    android:layout_height="wrap_content"
                    android:textSize="20sp"
                    android:text="8">
                </Button>

                <Button
                    android:id="@+id/btn9"
                    android:layout_width="80dp"
                    android:layout_height="wrap_content"
                    android:textSize="20sp"
                    android:text="9">
                </Button>
            </TableRow>
        </TableLayout>

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:orientation="vertical">
                <Button
                    android:id="@+id/btnAdd"
                    android:layout_width="match_parent"
                    android:layout_height="60dp"
                    android:textSize="25sp"
                    android:layout_margin="3dp">
                </Button>

                <Button
                    android:id="@+id/btnSub"
                    android:layout_width="match_parent"
                    android:layout_height="60dp"
                    android:textSize="25sp"
                    android:layout_margin="3dp">
                </Button>

                <Button
                    android:id="@+id/btnMul"
                    android:layout_width="match_parent"
                    android:layout_height="60dp"
                    android:textSize="25sp"
                    android:layout_margin="3dp">
                </Button>

                <TextView
                    android:id="@+id/textView"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:text="계산결과 : "
                    android:textColor="#FF0000"
                    android:textSize="30sp"/>

        </LinearLayout>

    </LinearLayout>

</androidx.constraintlayout.widget.ConstraintLayout>
```

