package com.example.tanya.shotscountingkotlin

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet

/**
 * Created by Tanya on 02.07.2017.
 */

class OpenGLView : GLSurfaceView {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        setEGLContextClientVersion(2)
        //preserveEGLContextOnPause = true
        setRenderer(OpenGLRenderer(context))
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
}
