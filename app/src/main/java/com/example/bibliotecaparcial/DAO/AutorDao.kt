package com.example.bibliotecaparcial.Dao

import androidx.room.*
import com.example.bibliotecaparcial.Model.Autor

@Dao
interface AutorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(autor: Autor)

    @Query("SELECT * FROM autores WHERE autor_id = :autorId")
    suspend fun getById(autorId: Int): Autor?

    @Query("SELECT * FROM autores")
    suspend fun getAllAutores(): List<Autor>

    @Query("DELETE FROM autores WHERE autor_id = :autorId")
    suspend fun deleteById(autorId: Int)

    @Query("DELETE FROM autores")
    suspend fun deleteAll()

    @Update
    suspend fun update(autor: Autor)
}
