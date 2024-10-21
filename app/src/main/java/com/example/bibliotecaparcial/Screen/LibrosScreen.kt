package com.example.bibliotecaparcial.Screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bibliotecaparcial.Model.Libro
import com.example.bibliotecaparcial.Repository.LibroRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LibrosScreen(libroRepository: LibroRepository, onBackClick: NavHostController) {
    var titulo by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var genero by remember { mutableStateOf("") } // Nueva propiedad para el género
    var libroId by remember { mutableStateOf("") }
    var libros by remember { mutableStateOf(listOf<Libro>()) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Función para limpiar los campos
    fun clearInputs() {
        titulo = ""
        autor = ""
        genero = ""
        libroId = ""
    }

    // Cargar la lista de libros
    fun loadLibros() {
        scope.launch {
            libros = withContext(Dispatchers.IO) {
                libroRepository.getAllLibros()
            }
        }
    }

    // Cargar libros al iniciar la pantalla
    LaunchedEffect(Unit) {
        loadLibros()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Registro de Libros", style = MaterialTheme.typography.titleLarge, color = Color(0xFF6200EE))

        TextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text(text = "Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = autor,
            onValueChange = { autor = it },
            label = { Text(text = "Autor") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = genero,
            onValueChange = { genero = it },
            label = { Text(text = "Género") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = libroId,
            onValueChange = {
                if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                    libroId = it
                }
            },
            label = { Text(text = "Libro ID (opcional para actualizar)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (titulo.isNotBlank() && autor.isNotBlank() && genero.isNotBlank()) {
                    val libro = Libro(
                        titulo = titulo,
                        autor = autor,
                        genero = genero
                    )
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            if (libroId.isNotBlank()) {
                                libroRepository.update(libro.copy(libro_id = libroId.toInt()))
                            } else {
                                libroRepository.insert(libro)
                            }
                            loadLibros() // Actualizar la lista después de insertar/actualizar
                        }
                        Toast.makeText(context, "Libro ${if (libroId.isNotBlank()) "actualizado" else "registrado"}", Toast.LENGTH_SHORT).show()
                        clearInputs()
                    }
                } else {
                    Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Registrar/Actualizar Libro", color = Color.White)
        }

        // Lista de libros
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(libros) { libro ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Text("${libro.libro_id}: ${libro.titulo} - ${libro.autor} - ${libro.genero}", modifier = Modifier.weight(1f))
                    Button(
                        onClick = {
                            libroId = libro.libro_id.toString()
                            titulo = libro.titulo
                            autor = libro.autor
                            genero = libro.genero // Cargar el género para modificar
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                        modifier = Modifier.padding(end = 4.dp)
                    ) {
                        Text(text = "Modificar")
                    }
                    Button(
                        onClick = {
                            scope.launch {
                                withContext(Dispatchers.IO) {
                                    libroRepository.deleteById(libro.libro_id)
                                    loadLibros() // Recargar la lista después de eliminar
                                }
                                Toast.makeText(context, "Libro eliminado", Toast.LENGTH_SHORT).show()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336))
                    ) {
                        Text(text = "Eliminar")
                    }
                }
            }
        }

        Button(
            onClick = { onBackClick },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF44336)),
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text(text = "Volver al Menú Principal", color = Color.White)
        }
    }
}
