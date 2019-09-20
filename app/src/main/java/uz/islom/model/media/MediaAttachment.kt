package uz.islom.model.media

import java.io.File

sealed class MediaAttachment(
        val remoteFile: String? = null,
        val localFile: File? = null) {

    data class PhotoAttachment(val width: Int, val height: Int) : MediaAttachment()

    data class AudioAttachment(val duration: Int) : MediaAttachment()

    data class ViewAttachment(val width: Int, val height: Int, val duration: Int) : MediaAttachment()


}