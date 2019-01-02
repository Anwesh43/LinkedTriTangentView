package com.anwesh.uiprojects.linkedtritangentstepview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anwesh.uiprojects.tritangentstepview.TriTangentStepView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TriTangentStepView.create(this)
    }
}
