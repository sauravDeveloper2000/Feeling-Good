package com.example.feelinggood.auth.ui.registration_screen

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.feelinggood.R
import com.example.feelinggood.auth.user_actions.EventsOnRegistrationScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    registrationScreenViewModel: RegistrationScreenViewModel,
    navigateToLoginScreen: () -> Unit
) {
    val nameRegex = Regex("^[a-zA-Z][a-z\\sA-Z]{2,49}\$")
    val context = LocalContext.current

    var nameFieldError by remember { mutableStateOf(false) }
    var nameFieldErrorCause by remember {
        mutableStateOf<String?>(null)
    }

    var emailFieldError by remember { mutableStateOf(false) }
    var emailFieldErrorCause by remember {
        mutableStateOf<String?>(null)
    }

    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    var newPasswordError by remember {
        mutableStateOf(false)
    }
    var newPasswordErrorCause by remember {
        mutableStateOf<String?>(null)
    }

    var confirmPasswordError by remember {
        mutableStateOf(false)
    }
    var confirmPasswordErrorCause by remember {
        mutableStateOf<String?>(null)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.app_name))
            })
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    /**
                     *  Name Field Validation
                     */
                    if (registrationScreenViewModel.name.isNotEmpty()) {
                        if (registrationScreenViewModel.name.matches(nameRegex)) {
                            nameFieldError = false
                            nameFieldErrorCause = null
                        } else {
                            nameFieldError = true
                            nameFieldErrorCause =
                                "Name must contain alphabets only and length should be in between 3 and 50."
                        }
                    } else {
                        nameFieldError = true
                        nameFieldErrorCause = "Name field is empty."
                    }

                    /**
                     * Email-Id validation
                     */
                    if (registrationScreenViewModel.emailId.isNotEmpty()) {
                        if (Patterns.EMAIL_ADDRESS.matcher(registrationScreenViewModel.emailId)
                                .matches()
                        ) {
                            emailFieldError = false
                            emailFieldErrorCause = null
                        } else {
                            emailFieldError = true
                            emailFieldErrorCause =
                                "Enter valid email-id without any spaces specifically in the end."
                        }
                    } else {
                        emailFieldError = true
                        emailFieldErrorCause = "Please enter your email-id"
                    }

                    /**
                     *  New Password and Confirm Password Validation.
                     */
                    if (registrationScreenViewModel.newPassword.isNotEmpty() && registrationScreenViewModel.confirmNewPassword.isNotEmpty()) {
                        if (registrationScreenViewModel.newPassword.length in 6..15 && registrationScreenViewModel.confirmNewPassword.length in 6..15) {
                            if (registrationScreenViewModel.newPassword == registrationScreenViewModel.confirmNewPassword) {
                                newPasswordError = false
                                newPasswordErrorCause = null

                                confirmPasswordError = false
                                confirmPasswordErrorCause = null
                            } else {
                                newPasswordError = true
                                confirmPasswordError = true

                                newPasswordErrorCause = "Password is not matching"
                                confirmPasswordErrorCause = "Password is not matching"
                            }
                        } else {
                            newPasswordError =
                                registrationScreenViewModel.newPassword.length !in 6..15
                            newPasswordErrorCause =
                                if (registrationScreenViewModel.newPassword.length in 6..15) null else "Password length should in b/w 6 and 15"

                            confirmPasswordError =
                                registrationScreenViewModel.confirmNewPassword.length !in 6..15
                            confirmPasswordErrorCause =
                                if (registrationScreenViewModel.confirmNewPassword.length in 6..15) null else "Confirm Password length should in b/w 6..15"
                        }
                    } else {
                        newPasswordError = registrationScreenViewModel.newPassword.isEmpty()
                        newPasswordErrorCause =
                            if (newPasswordError) "Please enter your new password!!" else null

                        confirmPasswordError =
                            registrationScreenViewModel.confirmNewPassword.isEmpty()
                        confirmPasswordErrorCause =
                            if (confirmPasswordError) "Please enter your new confirm password!!" else null
                    }

                    if (nameFieldError || confirmPasswordError || newPasswordError || emailFieldError) {
                        return@ExtendedFloatingActionButton
                    } else {
                        registrationScreenViewModel.createAccount(
                            successCase = {
                                Toast.makeText(
                                    context,
                                    "Account Created Successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            failureCase = {
                                Toast.makeText(
                                    context,
                                    "Account Creation got failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                }
            ) {
                Icon(imageVector = Icons.Default.Create, contentDescription = "Create Account")
                Text(text = "Create")
            }
        }
    ) { innerPadding ->
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Create Account")
                /**
                 * Name Field
                 */
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = registrationScreenViewModel.name,
                    onValueChange = { name ->
                        registrationScreenViewModel.onEvent(
                            EventsOnRegistrationScreen.OnNameFieldClick(
                                name = name
                            )
                        )
                    },
                    label = {
                        Text(text = "Name")
                    },
                    isError = nameFieldError,
                    supportingText = {
                        nameFieldErrorCause?.let {
                            Text(text = it)
                        }
                    }
                )
                /**
                 * Email-ID Field
                 */
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = registrationScreenViewModel.emailId,
                    onValueChange = { emailId ->
                        registrationScreenViewModel.onEvent(
                            EventsOnRegistrationScreen.OnEmailIdClick(
                                emailId = emailId
                            )
                        )
                    },
                    label = {
                        Text(text = "Email-ID")
                    },
                    isError = emailFieldError,
                    supportingText = {
                        emailFieldErrorCause?.let {
                            Text(text = it)
                        }
                    }
                )
                /**
                 * New Password Field
                 */
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = registrationScreenViewModel.newPassword,
                    onValueChange = { newPassword ->
                        registrationScreenViewModel.onEvent(
                            EventsOnRegistrationScreen.OnNewPasswordClick(
                                newPassword = newPassword
                            )
                        )
                    },
                    isError = newPasswordError,
                    supportingText = {
                        newPasswordErrorCause?.let {
                            Text(text = it)
                        }
                    },
                    label = {
                        Text(text = "New Password")
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val image =
                            if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                        IconButton(onClick = {
                            isPasswordVisible = !isPasswordVisible
                        }) {
                            Icon(imageVector = image, contentDescription = "Password Visibility")
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                /**
                 * Confirm New Password Field
                 */
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = registrationScreenViewModel.confirmNewPassword,
                    onValueChange = { confirmNewPassword ->
                        registrationScreenViewModel.onEvent(
                            EventsOnRegistrationScreen.OnConfirmPasswordClick(
                                confirmPassword = confirmNewPassword
                            )
                        )
                    },
                    isError = confirmPasswordError,
                    supportingText = {
                        confirmPasswordErrorCause?.let {
                            Text(text = it)
                        }
                    },
                    label = {
                        Text(text = "Confirm New Password")
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {
                            isPasswordVisible = !isPasswordVisible
                        }) {
                            var image =
                                if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            Icon(imageVector = image, contentDescription = "Password Visibility")
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {

                    /**
                     * If user already have an account, then navigate him to Login Screen.
                     */
                    Text(text = "Already have an account!!")
                    TextButton(onClick = navigateToLoginScreen) {
                        Text(text = "Login")
                    }
                }
            }
        }
    }
}