package com.example.ngo.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ngo.ui.theme.NGOTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.util.Log
import com.google.firebase.FirebaseApp


class RegisterNGO : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            val navController = rememberNavController()
            RegisterNGOScreen(navController)
        }
    }
}

@Composable
fun RegisterNGOScreen(navController: NavController?) {
    val name = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val missionStatement = remember { mutableStateOf("") }
    val registrationNumber = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Register your NGO",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                InputField(label = "NGO Name", placeholder = "Enter NGO name", value = name)
                Spacer(modifier = Modifier.height(10.dp))
                InputField(label = "Headquarters", placeholder = "Enter address", value = address, singleLine = false)
                Spacer(modifier = Modifier.height(10.dp))
                InputField(label = "Contact", placeholder = "Phone number", value = phone, keyboardType = KeyboardType.Phone)
                Spacer(modifier = Modifier.height(10.dp))
                InputField(label = "Mission Statement", placeholder = "Describe your mission", value = missionStatement, singleLine = false)
                Spacer(modifier = Modifier.height(10.dp))
                InputField(label = "Registration Number", placeholder = "Enter registration number", value = registrationNumber)

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                    /* Handle Next Button Click */
                                val db = Firebase.firestore

                                val ngoData = hashMapOf(
                                    "name" to name.value,
                                    "address" to address.value,
                                    "phone" to phone.value,
                                    "missionStatement" to missionStatement.value,
                                    "registrationNumber" to registrationNumber.value
                                )

                        Log.d("NGO_DEBUG", "Attempting to add NGO: $ngoData")

                        db.collection("ngos")
                                    .add(ngoData)
                                    .addOnSuccessListener {
                                        // Navigate only after successfully adding data
                                        navController?.navigate("register_event") {
                                            popUpTo("register_ngo") {
                                                inclusive = true
                                            }
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("FirestoreError", "Failed to register NGO", e)
                                    }

                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Next", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun InputField(
    label: String,
    placeholder: String,
    value: MutableState<String>,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 6.dp)
        )
        OutlinedTextField(
            value = value.value,
            onValueChange = { value.value = it },
            placeholder = { Text(text = placeholder, color = Color.Gray) },
            shape = RoundedCornerShape(10.dp),
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterNGOPreview() {
    NGOTheme {
        RegisterNGOScreen(navController = null)
    }
}
