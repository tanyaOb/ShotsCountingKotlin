package com.example.tanya.shotscountingkotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLUtils

/**
 * Created by Tanya on 03.07.2017.
 */

class Texture(ctx: Context) {

    private val m_iTextureId: Int

    init {
        m_iTextureId = loadTexture(ctx, R.drawable.image_projectr)
    }

    fun setTexture() {
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, m_iTextureId)
    }

    private fun loadTexture(ctx: Context, rscId: Int): Int {
        val iTextureId = IntArray(1)
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glGenTextures(1, iTextureId, 0)

        if (iTextureId[0] != 0) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, iTextureId[0])
            val options = BitmapFactory.Options()
            options.inScaled = false

            val bitmap = BitmapFactory.decodeResource(ctx.resources, rscId, options)
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)
            bitmap.recycle()
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST)
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST)
        } else {
            throw RuntimeException("error loading texture")
        }

        return iTextureId[0]
    }
}
