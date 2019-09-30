package uz.islom.model.db

import uz.islom.model.media.MediaAttachment

data class User(val id: Long,
                val name: String,
                val image: String)