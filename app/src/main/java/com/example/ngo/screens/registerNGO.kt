package com.example.ngoregistration

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

class RegisterNGO : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Register your NGO",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()  // Ensures text is centered properly

            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        // Input Fields
        InputField(label = "NGO", placeholder = "Name", value = name)

        Spacer(modifier = Modifier.height(20.dp))

        InputField(label = "Headquarters", placeholder = "Address", value = address, singleLine = false)

        Spacer(modifier = Modifier.height(20.dp))

        InputField(label = "Contact", placeholder = "Phone number", value = phone, keyboardType = KeyboardType.Phone)

        Spacer(modifier = Modifier.height(20.dp))

        InputField(label = "About", placeholder = "Mission Statement", value = missionStatement, singleLine = false)

        Spacer(modifier = Modifier.height(20.dp))

        InputField(label = "Registration Number", placeholder = "Business registration number", value = registrationNumber)

        Spacer(modifier = Modifier.height(20.dp))

        // Next Button
        Button(
            onClick = { /* Handle Next Button Click */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Next", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
            fontWeight = FontWeight.SemiBold,
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
