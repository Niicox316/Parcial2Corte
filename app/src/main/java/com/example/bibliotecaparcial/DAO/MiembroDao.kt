package com.example.bibliotecaparcial.Dao

import androidx.room.*
import com.example.bibliotecaparcial.Model.Miembro

@Dao
interface MiembroDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(miembro: Miembro)

    @Query("SELECT * FROM miembros WHERE miembro_id = :miembroId")
    suspend fun getById(miembroId: Int): Miembro?

    @Query("SELECT * FROM miembros")
    suspend fun getAllMiembros(): List<Miembro>

    @Query("DELETE FROM miembros WHERE miembro_id = :miembroId")
    suspend fun deleteById(miembroId: Int)

    @Query("DELETE FROM miembros")
    suspend fun deleteAll()

    @Update
    suspend fun update(miembro: Miembro)
}
