package com.hakan.naber.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime
import java.util.*

@Entity
data class Message (
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),

    val body: String,

    val created_at: OffsetDateTime?,

    val  updated_at: OffsetDateTime? = null


)