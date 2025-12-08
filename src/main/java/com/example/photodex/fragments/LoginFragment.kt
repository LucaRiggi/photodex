package com.example.myapplication.fragments

import com.example.photodex.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.photodex.viewmodel.LoginViewModel

// ----- Enum to switch between screen -----
enum class Mode {
    LOGIN,
    REGISTER
}



class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    LoginScreen (
                        vm = viewModel,
                        onSuccess = {
                            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                        }
                    )
                }
            }
        }
    }
}

// ----- Compose UI -----

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    vm: LoginViewModel,
    onSuccess: () -> Unit
) {
    val isLoading by vm.isLoading.collectAsState()
    val errorMessage by vm.errorMessage.collectAsState()
    val loginSuccess by vm.loginSuccess.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmation by remember { mutableStateOf("") }
    var mode by remember { mutableStateOf(Mode.LOGIN) }

    if (loginSuccess) {
        onSuccess()
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("PhotoDex", textAlign = TextAlign.Center, fontWeight = FontWeight.Bold) },

            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it},
                label = { Text("Email") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(Modifier.height(24.dp))
            if (mode == Mode.REGISTER) {
                OutlinedTextField(
                    value = confirmation,
                    onValueChange = { confirmation = it },
                    label = { Text("Confirm Password") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(Modifier.height(24.dp))
            }
            Text(
                text = if (mode == Mode.LOGIN) "Don't have an account? Register" else "Already have an account? Log in",
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    mode = if (mode == Mode.LOGIN) Mode.REGISTER else Mode.LOGIN
                }
            )
            Spacer(Modifier.height(24.dp))
            // show error message if there is
            if (errorMessage != null) {
                Text(
                    // !! is safe here
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(24.dp))
            }
            Button(onClick = {
                if (mode == Mode.LOGIN) {
                    vm.login(email, password)
                } else {
                    vm.register(email, password, confirmation)
                }
            }) {
                // show cool loading bar if loading (https://developer.android.com/develop/ui/compose/components/progress)
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(if (mode == Mode.LOGIN) "Login!" else "Register!")
                }
            }

            Spacer(Modifier.height(24.dp))
            }
        }

}
