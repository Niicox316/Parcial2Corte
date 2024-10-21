package com.example.bibliotecaparcial.Repository

import com.example.bibliotecaparcial.Dao.PrestamoDao
import com.example.bibliotecaparcial.Model.Prestamo

class PrestamoRepository(private val prestamoDao: PrestamoDao) {
    suspend fun insert(prestamo: Prestamo) {
        prestamoDao.insert(prestamo)
    }

    suspend fun getById(prestamoId: Int): Prestamo? {
        return prestamoDao.getById(prestamoId)
    }

    suspend fun getAllPrestamos(): List<Prestamo> {
        return prestamoDao.getAllPrestamos()
    }

    suspend fun deleteById(prestamoId: Int) {
        prestamoDao.deleteById(prestamoId)
    }

    suspend fun deleteAll() {
        prestamoDao.deleteAll()
    }

    suspend fun update(prestamo: Prestamo) {
        prestamoDao.update(prestamo)
    }
}
