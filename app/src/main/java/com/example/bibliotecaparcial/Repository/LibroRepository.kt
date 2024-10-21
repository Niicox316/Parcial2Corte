package com.example.bibliotecaparcial.Repository

import com.example.bibliotecaparcial.Dao.LibroDao
import com.example.bibliotecaparcial.Model.Libro

class LibroRepository(private val libroDao: LibroDao) {
    suspend fun insert(libro: Libro) {
        libroDao.insert(libro)
    }

    suspend fun getById(libroId: Int): Libro? {
        return libroDao.getById(libroId)
    }

    suspend fun getAllLibros(): List<Libro> {
        return libroDao.getAllLibros()
    }

    suspend fun deleteById(libroId: Int) {
        libroDao.deleteById(libroId)
    }

    suspend fun deleteAll() {
        libroDao.deleteAll()
    }

    suspend fun update(libro: Libro) {
        libroDao.update(libro)
    }
}
