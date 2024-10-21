package com.example.bibliotecaparcial.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "miembros")
data class Miembro(
    @PrimaryKey(autoGenerate = true) val miembro_id: Int = 0,
    val nombre: String,
    val apellido: String,
    val edad: Int
)
