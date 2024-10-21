package com.example.bibliotecaparcial.Screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.bibliotecaparcial.Model.Miembro
import com.example.bibliotecaparcial.R
import com.example.bibliotecaparcial.Repository.MiembroRepository
import com.example.bibliotecaparcial.ui.theme.Beige500
import com.example.bibliotecaparcial.ui.theme.Brown500
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MiembroScreen(miembroRepository: MiembroRepository, onBackClick: NavHostController) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var miembroId by remember { mutableStateOf("") }
    var miembros by remember { mutableStateOf(listOf<Miembro>()) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(Beige500)
    ) {
        Text(
            text = "Registro de Miembros",
            style = MaterialTheme.typography.titleLarge,
            color = Brown500
        )

        // Imágen relacionada con la biblioteca
        Image(
            painter = painterResource(id = R.drawable.libro_image), // Asegúrate de tener esta imagen en res/drawable
            contentDescription = "Libros",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

    }



    // Función para limpiar los campos
    fun clearInputs() {
        nombre = ""
        apellido = ""
        edad = ""
        miembroId = ""
    }

    // Cargar la lista de miembros
    fun loadMiembros() {
        scope.launch {
            miembros = withContext(Dispatchers.IO) {
                miembroRepository.getAllMiembros()
            }
        }
    }

    // Cargar miembros al iniciar la pantalla
    LaunchedEffect(Unit) {
        loadMiembros()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Registro de Miembros", style = MaterialTheme.typography.titleLarge, color = Color(0xFF6200EE))

        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text(text = "Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = apellido,
            onValueChange = { apellido = it },
            label = { Text(text = "Apellido") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = edad,
            onValueChange = {
                if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                    edad = it
                }
            },
            label = { Text(text = "Edad") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = miembroId,
            onValueChange = {
                if (it.isEmpty() || it.all { char -> char.isDigit() }) {
                    miembroId = it
                }
            },
            label = { Text(text = "Miembro ID (opcional para actualizar)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (nombre.isNotBlank() && apellido.isNotBlank() && edad.isNotBlank()) {
                    val miembro = Miembro(
                        nombre = nombre,
                        apellido = apellido,
                        edad = edad.toInt()
                    )
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            if (miembroId.isNotBlank()) {
                                miembroRepository.update(miembro.copy(miembro_id = miembroId.toInt()))
                            } else {
                                miembroRepository.insert(miembro)
                            }
                            loadMiembros() // Actualizar la lista después de insertar/actualizar
                        }
                        Toast.makeText(context, "Miembro ${if (miembroId.isNotBlank()) "actualizado" else "registrado"}", Toast.LENGTH_SHORT).show()
                        clearInputs()
                    }
                } else {
                    Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Registrar/Actualizar Miembro", color = Color.White)
        }

        // Lista de miembros
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(miembros) { miembro ->
                Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Text("${miembro.miembro_id}: ${miembro.nombre} ${miembro.apellido} - Edad: ${miembro.edad}", modifier = Modifier.weight(1f))
                    Button(
                        onClick = {
                            miembroId = miembro.miembro_id.toString()
                            nombre = miembro.nombre
                            apellido = miembro.apellido
                            edad = miembro.edad.toString() // Cargar la edad para modificar
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
                                    miembroRepository.deleteById(miembro.miembro_id)
                                    loadMiembros() // Recargar la lista después de eliminar
                                }
                                Toast.makeText(context, "Miembro eliminado", Toast.LENGTH_SHORT).show()
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


