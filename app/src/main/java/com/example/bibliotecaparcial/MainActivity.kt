package com.example.bibliotecaparcial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bibliotecaparcial.Database.AppDatabase
import com.example.bibliotecaparcial.Repository.AutorRepository
import com.example.bibliotecaparcial.Repository.LibroRepository
import com.example.bibliotecaparcial.Repository.MiembroRepository
import com.example.bibliotecaparcial.Repository.PrestamoRepository
import com.example.bibliotecaparcial.Screen.*
import com.example.bibliotecaparcial.ui.theme.BibliotecaParcialTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BibliotecaParcialTheme {
                // Configura la base de datos y los repositorios
                val database = AppDatabase.getDatabase(this)
                val libroRepository = remember { LibroRepository(database.libroDao()) }
                val autorRepository = remember { AutorRepository(database.autorDao()) }
                val miembroRepository = remember { MiembroRepository(database.miembroDao()) }
                val prestamoRepository = remember { PrestamoRepository(database.prestamoDao()) }

                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "items") {
                        composable("items") {
                            Items(
                                navController = navController,
                                libroRepository = libroRepository,
                                autorRepository = autorRepository,
                                miembroRepository = miembroRepository,
                                prestamoRepository = prestamoRepository
                            )
                        }
                        composable("libros") {
                            LibrosScreen(libroRepository, navController)
                        }
                        composable("autores") {
                            AutorScreen(autorRepository, navController)
                        }
                        composable("miembros") {
                            MiembroScreen(miembroRepository, navController)
                        }
                        composable("prestamos") {
                            PrestamoScreen(prestamoRepository, navController)
                        }
                    }
                }
            }
        }
    }
}
