package com.example.bibliotecaparcial.Dao

import androidx.room.*
import com.example.bibliotecaparcial.Model.Prestamo

@Dao
interface PrestamoDao {

    // Función para insertar un nuevo préstamo
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prestamo: Prestamo)

    // Función para obtener un préstamo por su ID
    @Query("SELECT * FROM prestamos WHERE prestamo_id = :prestamoId")
    suspend fun getById(prestamoId: Int): Prestamo?

    // Función para obtener todos los préstamos
    @Query("SELECT * FROM prestamos")
    suspend fun getAllPrestamos(): List<Prestamo>

    // Función para eliminar un préstamo por su ID
    @Query("DELETE FROM prestamos WHERE prestamo_id = :prestamoId")
    suspend fun deleteById(prestamoId: Int)

    // Función para eliminar todos los préstamos
    @Query("DELETE FROM prestamos")
    suspend fun deleteAll()

    // Función para actualizar un préstamo existente
    @Update
    suspend fun update(prestamo: Prestamo)
}
