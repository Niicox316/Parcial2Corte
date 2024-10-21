package com.example.bibliotecaparcial.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.bibliotecaparcial.Dao.AutorDao
import com.example.bibliotecaparcial.Dao.LibroDao
import com.example.bibliotecaparcial.Dao.MiembroDao
import com.example.bibliotecaparcial.Dao.PrestamoDao
import com.example.bibliotecaparcial.Model.Autor
import com.example.bibliotecaparcial.Model.Libro
import com.example.bibliotecaparcial.Model.Miembro
import com.example.bibliotecaparcial.Model.Prestamo

@Database(entities = [Autor::class, Libro::class, Miembro::class, Prestamo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun autorDao(): AutorDao
    abstract fun libroDao(): LibroDao
    abstract fun miembroDao(): MiembroDao
    abstract fun prestamoDao(): PrestamoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "biblioteca_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
