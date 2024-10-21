package com.example.bibliotecaparcial.Repository

import com.example.bibliotecaparcial.Dao.MiembroDao
import com.example.bibliotecaparcial.Model.Miembro

class MiembroRepository(private val miembroDao: MiembroDao) {
    suspend fun insert(miembro: Miembro) {
        miembroDao.insert(miembro)
    }

    suspend fun getById(miembroId: Int): Miembro? {
        return miembroDao.getById(miembroId)
    }

    suspend fun getAllMiembros(): List<Miembro> {
        return miembroDao.getAllMiembros()
    }

    suspend fun deleteById(miembroId: Int) {
        miembroDao.deleteById(miembroId)
    }

    suspend fun deleteAll() {
        miembroDao.deleteAll()
    }

    suspend fun update(miembro: Miembro) {
        miembroDao.update(miembro)
    }
}
