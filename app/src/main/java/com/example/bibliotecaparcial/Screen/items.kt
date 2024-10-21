package com.example.bibliotecaparcial.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.bibliotecaparcial.R // Asegúrate de importar tu paquete de recursos
import com.example.bibliotecaparcial.Repository.AutorRepository
import com.example.bibliotecaparcial.Repository.LibroRepository
import com.example.bibliotecaparcial.Repository.MiembroRepository
import com.example.bibliotecaparcial.Repository.PrestamoRepository

@Composable
fun Items(
    navController: NavController,
    libroRepository: LibroRepository,
    autorRepository: AutorRepository,
    miembroRepository: MiembroRepository,
    prestamoRepository: PrestamoRepository
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen en la parte superior
        Image(
            painter = painterResource(id = R.drawable.library_background), // Cambia el nombre de la imagen aquí
            contentDescription = "Imagen de Biblioteca",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Ajusta la altura según sea necesario
                .padding(bottom = 16.dp)
        )

        Text("BIBLIOTECA", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("libros") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Libros")
        }

        Button(
            onClick = { navController.navigate("autores") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Autores")
        }

        Button(
            onClick = { navController.navigate("miembros") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Miembros")
        }

        Button(
            onClick = { navController.navigate("prestamos") },
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        ) {
            Text("Préstamos")
        }
    }
}
