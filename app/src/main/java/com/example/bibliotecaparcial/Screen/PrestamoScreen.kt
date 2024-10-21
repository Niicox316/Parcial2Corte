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
import com.example.bibliotecaparcial.Model.Prestamo
import com.example.bibliotecaparcial.Repository.PrestamoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PrestamoScreen(prestamoRepository: PrestamoRepository, onBackClick: NavHostController) {
    var fechaPrestamo by remember { mutableStateOf("") }
    var fechaDevolucion by remember { mutableStateOf("") }
    var libroId by remember { mutableStateOf("") }
    var prestamos by remember { mutableStateOf(listOf<Prestamo>()) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Función para limpiar los campos
    fun clearInputs() {
        fechaPrestamo = ""
        fechaDevolucion = ""
        libroId = ""
    }

    // Cargar préstamos al iniciar la pantalla
  //  LaunchedEffect(Unit) {
    //    loadPrestamos()
   // }

    // Cargar la lista de préstamos
    suspend fun loadPrestamos() {
        withContext(Dispatchers.IO) {
            prestamos = prestamoRepository.getAllPrestamos()
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Registro de Préstamos", style = MaterialTheme.typography.titleLarge, color = Color(0xFF6200EE))

        TextField(
            value = fechaPrestamo,
            onValueChange = { fechaPrestamo = it },
            label = { Text(text = "Fecha de Préstamo (dd/MM/yyyy)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = fechaDevolucion,
            onValueChange = { fechaDevolucion = it },
            label = { Text(text = "Fecha de Devolución (dd/MM/yyyy)") },
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
            label = { Text(text = "Libro ID") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (fechaPrestamo.isNotBlank() && fechaDevolucion.isNotBlank() && libroId.isNotBlank()) {
                    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val prestamo = Prestamo(
                        libro_id = libroId.toInt(),
                        fecha_prestamo = (formatter.parse(fechaPrestamo) ?: Date()).toString(),
                        fecha_devolucion = (formatter.parse(fechaDevolucion) ?: Date()).toString()
                    )
                    scope.launch {
                        withContext(Dispatchers.IO) {
                            prestamoRepository.insert(prestamo)
                            loadPrestamos()
                        }
                        Toast.makeText(context, "Préstamo Registrado", Toast.LENGTH_SHORT).show()
                        clearInputs()
                    }
                } else {
                    Toast.makeText(context, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Registrar Préstamo", color = Color.White)
        }

        // Lista de préstamos
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(prestamos) { prestamo ->
                Text("ID: ${prestamo.prestamo_id}, Libro ID: ${prestamo.libro_id}, " +
                        "Fecha de Préstamo: ${prestamo.fecha_prestamo}, " +
                        "Fecha de Devolución: ${prestamo.fecha_devolucion}")
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
