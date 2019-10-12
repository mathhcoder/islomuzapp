package uz.islom.model.db

import uz.islom.model.app.Madhab

data class User(val id: Long,
                val name: String,
                val image: String,
                val university: University,
                val madhab: Madhab)