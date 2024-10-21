package com.example.bibliotecaparcial.Dao

import androidx.room.*
import com.example.bibliotecaparcial.Model.Libro

@Dao
interface LibroDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(libro: Libro)

    @Query("SELECT * FROM libros WHERE libro_id = :libroId")
    suspend fun getById(libroId: Int): Libro?

    @Query("SELECT * FROM libros")
    suspend fun getAllLibros(): List<Libro>

    @Query("DELETE FROM libros WHERE libro_id = :libroId")
    suspend fun deleteById(libroId: Int)

    @Query("DELETE FROM libros")
    suspend fun deleteAll()

    @Update
    suspend fun update(libro: Libro)
}
