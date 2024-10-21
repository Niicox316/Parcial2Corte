package com.example.bibliotecaparcial.Repository

import com.example.bibliotecaparcial.Dao.AutorDao
import com.example.bibliotecaparcial.Model.Autor

class AutorRepository(private val autorDao: AutorDao) {
    suspend fun insert(autor: Autor) {
        autorDao.insert(autor)
    }

    suspend fun getById(autorId: Int): Autor? {
        return autorDao.getById(autorId)
    }

    suspend fun getAllAutores(): List<Autor> {
        return autorDao.getAllAutores()
    }

    suspend fun deleteById(autorId: Int) {
        autorDao.deleteById(autorId)
    }

    suspend fun deleteAll() {
        autorDao.deleteAll()
    }

    suspend fun update(autor: Autor) {
        autorDao.update(autor)
    }
}
