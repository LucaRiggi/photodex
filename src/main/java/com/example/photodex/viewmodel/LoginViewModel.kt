package com.example.photodex.viewmodel

import UserRepository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.photodex.data.database.AppDatabase
import com.example.photodex.data.user.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * View Model For Login
 */
class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        val dao = AppDatabase.getDatabase(context = application).userDao()
        repository = UserRepository(dao)

    }

    fun getUserByEmail(email: String) = viewModelScope.launch {
        repository.getUserByEmail(email)
    }

    fun loginUser(email: String, password: String) = viewModelScope.launch {
        repository.loginUser(email, password)
    }

    /**
     * Function to handle login (UI part)
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            if (email.isEmpty() || password.isEmpty()) {
                _errorMessage.value = "Uh oh. Email or password is empty"
                _isLoading.value = false
                // Intellij swears without @launch
                return@launch
            }

            try {
                val user = repository.loginUser(email, password)
                if (user != null) {
                    _loginSuccess.value = true
                } else {
                    _errorMessage.value = "Wrong email or password."
                }
            } catch (e: Exception) {
                _errorMessage.value = "ERROR! Please try again."
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Function to handle registration (UI part)
     */
    fun register(email: String, password: String, confirm: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            if (email.isEmpty() || password.isEmpty()) {
                _errorMessage.value = "Uh oh. Email or password is empty"
                _isLoading.value = false
                // Intellij swears without @launch
                return@launch
            }
            // confirming password
            if (password != confirm) {
                _errorMessage.value = "Passwords do not match."
                _isLoading.value = false
                return@launch
            }

            try {
                if (repository.getUserByEmail(email) != null) {
                    _errorMessage.value = "Email already registered."
                } else {
                    // create new user
                    val newUser = User(0, email, password)
                    repository.insertUser(newUser)
                    // log in after registration
                    _loginSuccess.value = true
                }
            } catch (e: Exception) {
                _errorMessage.value = "ERROR! Please try again."
            } finally {
                _isLoading.value = false
            }
        }
    }
}