package com.example.feelinggood.auth.ui.registration_screen

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
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
                            Toast.makeText(context, "Name is valid", Toast.LENGTH_SHORT).show()
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
                    label = {
                        Text(text = "New Password")
                    }
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
                    label = {
                        Text(text = "Confirm New Password")
                    }
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