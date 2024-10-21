package com.example.bibliotecaparcial.Screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun MainMenuScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Menú Principal", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("libros_screen") }) {
            Text("Gestión de Libros")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigate("autor_screen") }) {
            Text("Gestión de Autores")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigate("prestamo_screen") }) {
            Text("Gestión de Préstamos")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { navController.navigate("miembro_screen") }) {
            Text("Gestión de Miembros")
        }
    }

}
