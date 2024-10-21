package com.example.bibliotecaparcial.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "libros")
data class Libro(
    @PrimaryKey(autoGenerate = true) val libro_id: Int = 0,
    val titulo: String,
    val autor: String,
    val genero: String // Asegúrate de que esta propiedad esté presente
)
