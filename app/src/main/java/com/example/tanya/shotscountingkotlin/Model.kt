package com.example.tanya.shotscountingkotlin

/**
 * Created by Tanya on 03.07.2017.
 */

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

import android.content.Context
import android.opengl.GLES20

class Model(ctx: Context) {

    internal var m_vertexBuffer: FloatBuffer
    internal var m_indexBuffer: ShortBuffer
    internal var m_texture: Texture
    internal var m_iNumIndices = 6
    private var m_iProgramId = 0
    private var m_iVertexShaderId = 0
    private var m_iFragmentShaderId = 0
    //internal var vertices = floatArrayOf(-1.0f, -1.0f, 0.0f, 0.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f)
    internal var indices = shortArrayOf(0, 2, 1, 0, 3, 2)

//    internal var vertices = floatArrayOf(-1.0f, -1.0f, 0.0f, 0.0f, 0.0f,
//                                         -1.0f, 1.0f, 0.0f, 0.0f, 1.0f,
//                                          1.0f, 1.0f, 0.0f, 1.0f, 1.0f,
//                                          1.0f, -1.0f, 0.0f, 1.0f, 0.0f)

    internal var vertices = floatArrayOf(-1.0f, -1.0f, 0.0f, 0.0f, 0.0f,
            -1.0f, 1.0f, 0.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 0.0f, 0.0f, 1.0f)


    private var m_iPositionId = 0
    private var m_iTexCoordId = 0

    private val sVertexShader = "attribute vec3 in_position; attribute vec2 in_texCoord; varying vec2 texCoord; void main(){ gl_Position = vec4(in_position, 1); texCoord = in_texCoord;}"
    private val sFragmentShader = "precision mediump float; varying vec2 texCoord; uniform sampler2D sampler; void main(){gl_FragColor = texture2D(sampler, texCoord);} "

    init {

        val bbVertices = ByteBuffer.allocateDirect(vertices.size * 4)
        bbVertices.order(ByteOrder.nativeOrder())

        m_vertexBuffer = bbVertices.asFloatBuffer()
        m_vertexBuffer.put(vertices)
        m_vertexBuffer.position(0)

        val bbIndices = ByteBuffer.allocateDirect(indices.size * 4)
        bbIndices.order(ByteOrder.nativeOrder())

        m_indexBuffer = bbIndices.asShortBuffer()
        m_indexBuffer.put(indices)
        m_indexBuffer.position(0)

        //objects
        m_texture = Texture(ctx)

        m_iVertexShaderId = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER)
        m_iFragmentShaderId = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER)

        GLES20.glShaderSource(m_iVertexShaderId, sVertexShader)
        GLES20.glShaderSource(m_iFragmentShaderId, sFragmentShader)

        GLES20.glCompileShader(m_iVertexShaderId)
        GLES20.glCompileShader(m_iFragmentShaderId)

        m_iProgramId = GLES20.glCreateProgram()
        GLES20.glAttachShader(m_iProgramId, m_iVertexShaderId)
        GLES20.glAttachShader(m_iProgramId, m_iFragmentShaderId)
        GLES20.glLinkProgram(m_iProgramId)

        //attribute id's
        m_iPositionId = GLES20.glGetAttribLocation(m_iProgramId, "in_position")
        m_iTexCoordId = GLES20.glGetAttribLocation(m_iProgramId, "in_texCoord")
    }

    fun draw() {
        GLES20.glUseProgram(m_iProgramId)
        val iLocation = GLES20.glGetUniformLocation(m_iProgramId, "sampler")
        if (iLocation == -1)
            throw RuntimeException("invalid uniform location")

        GLES20.glUniform1i(iLocation, 0)

        m_vertexBuffer.position(0)
        val iPosId = m_iPositionId // //m_iProgramId;
        GLES20.glEnableVertexAttribArray(iPosId)
        GLES20.glVertexAttribPointer(iPosId, 3, GLES20.GL_FLOAT, false,
                20, m_vertexBuffer)

        m_vertexBuffer.position(3)
        val iTexCoordId = m_iTexCoordId //m_iTexCoordId;
        GLES20.glEnableVertexAttribArray(iTexCoordId)
        GLES20.glVertexAttribPointer(iTexCoordId, 2, GLES20.GL_FLOAT, false,
                20, m_vertexBuffer)

        m_texture.setTexture()

        GLES20.glDrawElements(GLES20.GL_TRIANGLES, m_iNumIndices,
                GLES20.GL_UNSIGNED_SHORT, m_indexBuffer)

        GLES20.glDisableVertexAttribArray(iPosId)
        GLES20.glDisableVertexAttribArray(iTexCoordId)
    }

}
