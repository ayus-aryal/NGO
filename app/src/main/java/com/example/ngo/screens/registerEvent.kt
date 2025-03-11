package com.example.ngo.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ngo.ui.theme.NGOTheme

class RegisterEvent : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            RegisterEventScreen(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterEventScreen(navController: NavController?) {
    val context = LocalContext.current
    val eventName = remember { mutableStateOf("") }
    val eventDescription = remember { mutableStateOf("") }
    val eventCategories = listOf("Health", "Education", "Environment", "Social")
    var selectedCategory by remember { mutableStateOf(eventCategories[0]) }
    val eventLocation = remember { mutableStateOf("Tap to pick location") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            "Register Event",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        // Event Name
        OutlinedTextField(
            value = eventName.value,
            onValueChange = { eventName.value = it },
            label = { Text("Event Name") },
            modifier = Modifier.fillMaxWidth()
        )

        // Event Description
        OutlinedTextField(
            value = eventDescription.value,
            onValueChange = { eventDescription.value = it },
            label = { Text("Event Description") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            maxLines = 4
        )

        // Event Category Dropdown
        ExposedDropdownMenuBox(
            expanded = false,
            onExpandedChange = {}
        ) {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true,
                label = { Text("Event Category") },
                modifier = Modifier.fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = false,
                onDismissRequest = {}
            ) {
                eventCategories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = { selectedCategory = category }
                    )
                }
            }
        }

        // Select Location Button (Opens Google Maps)
        Button(
            onClick = {
                try {
                    val gmmIntentUri = Uri.parse("geo:0,0?q=") // Opens Google Maps
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    context.startActivity(mapIntent)
                } catch (e: Exception) {
                    Toast.makeText(context, "Google Maps not found!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(eventLocation.value)
        }

        // Submit Event Button
        Button(
            onClick = {
                // Handle form submission (You can replace this with your actual logic)
                Toast.makeText(
                    context,
                    "Event Registered: ${eventName.value}",
                    Toast.LENGTH_SHORT
                ).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Event")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterEventPreview() {
    NGOTheme {
        RegisterEventScreen(navController = null)
    }
}
