package com.example.tanya.shotscountingkotlin

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by Tanya on 02.07.2017.
 */

class OpenGLRenderer(var m_ctx:Context): GLSurfaceView.Renderer {

    private var screenWidth: Float = 0.0f
    private var screenHeight: Float = 0.0f
    private var scale: Float = 0.0f
    private var touchX: Float = 0.0f
    private var touchY: Float = 0.0f


    internal lateinit var model:Model

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        GLES20.glClearColor(1f, 0f, 0f, 0f)
        model = Model(m_ctx)
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height);

    }

    override fun onDrawFrame(gl: GL10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        model.draw();
    }

    fun loadOrthoMatrix(matrix: FloatArray, left: Float, right: Float, bottom: Float, top: Float, near: Float, far: Float) {
        val r_l = right - left
        val t_b = top - bottom
        val f_n = far - near
        val tx = -(right + left) / (right - left)
        val ty = -(top + bottom) / (top - bottom)
        val tz = -(far + near) / (far - near)

        matrix[0] = 2.0f / r_l
        matrix[1] = 0.0f
        matrix[2] = 0.0f
        matrix[3] = tx

        matrix[4] = 0.0f
        matrix[5] = 2.0f / t_b
        matrix[6] = 0.0f
        matrix[7] = ty

        matrix[8] = 0.0f
        matrix[9] = 0.0f
        matrix[10] = 2.0f / f_n
        matrix[11] = tz

        matrix[12] = 0.0f
        matrix[13] = 0.0f
        matrix[14] = 0.0f
        matrix[15] = 1.0f
    }
}
