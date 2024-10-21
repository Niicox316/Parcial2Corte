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
import com.example.bibliotecaparcial.Model.Autor
import com.example.bibliotecaparcial.Repository.AutorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AutorScreen(autorRepository: AutorRepository, onBackClick: NavHostController) {
    var nombre by remember { mutableStateOf("") }
    var autorId by remember { mutableStateOf("") }
    var autores by remember { mutableStateOf(listOf<Autor>()) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Función para limpiar los campos
    fun clearInputs() {
        nombre = ""
        autorId = ""
    }

    // Cargar la lista de autores
    fun loadAutores() {
        scope.launch {
            autores = withContext(Dispatchers.IO) {
                autorRepository.getAllAutores()
            }
        }
    }

    // Cargar autores al iniciar la pantalla
    LaunchedEffect(Unit) {
        loadAutores()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Registro de Autores", style = MaterialTheme.typography.titleLarge, color = Color(0xFF6200EE))

        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text(text = "Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = autorId,
            onValueChange = {
                if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                    autorId = it
                }
            },
            label = { Text(text = "Autor ID (opcional para actualizar)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (nombre.isNotBlank()) {
                    val autor = Autor(
                        nombre = nombre
                    )
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            if (autorId.isNotBlank()) {
                                autorRepository.update(autor.copy(autor_id = autorId.toInt()))
                            } else {
                                autorRepository.insert(autor)
                            }
                            loadAutores() // Actualizar la lista después de insertar/actualizar
                        }
                        Toast.makeText(context, "Autor ${if (autorId.isNotBlank()) "actualizado" else "registrado"}", Toast.LENGTH_SHORT).show()
                        clearInputs()
                    }
                } else {
                    Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Registrar/Actualizar Autor", color = Color.White)
        }

        // Lista de autores
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(autores) { autor ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Text("${autor.autor_id}: ${autor.nombre}", modifier = Modifier.weight(1f))
                    Button(
                        onClick = {
                            autorId = autor.autor_id.toString()
                            nombre = autor.nombre
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
                                    autorRepository.deleteById(autor.autor_id)
                                    loadAutores() // Recargar la lista después de eliminar
                                }
                                Toast.makeText(context, "Autor eliminado", Toast.LENGTH_SHORT).show()
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
