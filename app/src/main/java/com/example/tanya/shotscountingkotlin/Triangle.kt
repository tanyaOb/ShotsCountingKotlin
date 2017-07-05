package com.example.tanya.shotscountingkotlin

import android.opengl.GLES20

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Created by Tanya on 02.07.2017.
 */

class Triangle {

    private val vertexBuffer: FloatBuffer
    private val vertices = floatArrayOf(0.0f, 0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f)
    private val color = floatArrayOf(0.0f, 0.6f, 1.0f, 1.0f)
    private val vertexShaderCode = "attribute vec4 vPosition; void main(){ gl_Position=vPosition;}"
    private val fragmentShaderCode = "precision mediump float; uniform vec4 vColor; void main(){gl_FragColor=vColor;} "
    private val shaderProgram: Int

    init {
        val bb = ByteBuffer.allocateDirect(vertices.size * 4)
        bb.order(ByteOrder.nativeOrder())
        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(vertices)
        vertexBuffer.position(0)
        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
        shaderProgram = GLES20.glCreateProgram()
        GLES20.glAttachShader(shaderProgram, vertexShader)
        GLES20.glAttachShader(shaderProgram, fragmentShader)
        GLES20.glLinkProgram(shaderProgram)
    }

    fun draw() {
        GLES20.glUseProgram(shaderProgram)
        val possitionAttribute = GLES20.glGetAttribLocation(shaderProgram, "vPosition")
        GLES20.glEnableVertexAttribArray(possitionAttribute)
        GLES20.glVertexAttribPointer(possitionAttribute, 3, GLES20.GL_FLOAT, false, 0, vertexBuffer)
        val colorUnifform = GLES20.glGetUniformLocation(shaderProgram, "vColor")
        GLES20.glUniform4fv(colorUnifform, 1, color, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertices.size / 3)
        GLES20.glDisableVertexAttribArray(possitionAttribute)
    }

    companion object {

        fun loadShader(type: Int, shaderCode: String): Int {
            val Shader = GLES20.glCreateShader(type)
            GLES20.glShaderSource(Shader, shaderCode)
            GLES20.glCompileShader(Shader)
            return Shader
        }
    }
}
