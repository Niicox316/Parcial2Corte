package com.example.bibliotecaparcial.Model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prestamos")
data class Prestamo(
    @PrimaryKey(autoGenerate = true) val prestamo_id: Int = 0,
    val libro_id: Int,
    val fecha_prestamo: String,
    val fecha_devolucion: String? = null // Puedes usar nullable si es opcional){}
)
