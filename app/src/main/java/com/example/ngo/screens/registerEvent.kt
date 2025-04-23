package com.example.ngo.screens

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.activity.compose.rememberLauncherForActivityResult
import com.google.firebase.firestore.FirebaseFirestore
import androidx.core.net.toUri

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
    val eventCategories = listOf("Health", "Education", "Environment")
    var selectedCategory by remember { mutableStateOf(eventCategories[0]) }
    var expanded by remember { mutableStateOf(false) }
    var eventLocation by remember { mutableStateOf("Tap to pick location") }

    val locationPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedUri: Uri? = result.data?.data
            eventLocation = selectedUri?.toString() ?: "Location Selected"
        }
    }

    // Firestore instance
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Register Event", fontSize = 22.sp, fontWeight = FontWeight.Bold)

        OutlinedTextField(
            value = eventName.value,
            onValueChange = { eventName.value = it },
            label = { Text("Event Name") },
            modifier = Modifier.fillMaxWidth()
        )

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
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true,
                label = { Text("Event Category") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                eventCategories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedCategory = category
                            expanded = false
                        }
                    )
                }
            }
        }

        // Select Location Button (Opens Google Maps Picker)
        Button(
            onClick = {
                val gmmIntentUri = "geo:0,0?q=".toUri()
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                locationPickerLauncher.launch(mapIntent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(eventLocation)
        }

        // Submit Button
        Button(
            onClick = {
                if (eventName.value.isNotEmpty() && eventDescription.value.isNotEmpty()) {
                    val eventData = hashMapOf(
                        "eventName" to eventName.value,
                        "eventDescription" to eventDescription.value,
                        "eventCategory" to selectedCategory,
                        "eventLocation" to eventLocation
                    )

                    db.collection("events")
                        .add(eventData)
                        .addOnSuccessListener {
                            Toast.makeText(
                                context,
                                "Event Registered Successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController?.navigate("events_screen")
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(
                                context,
                                "Error registering event: ${e.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } else {
                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
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
