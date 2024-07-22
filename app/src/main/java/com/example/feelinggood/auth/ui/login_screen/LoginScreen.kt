package com.example.feelinggood.auth.ui.login_screen

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.feelinggood.auth.user_actions.EventsOnLoginScreen
import com.example.feelinggood.core.components.VerticalSpace

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginScreenViewModel: LoginScreenViewModel,
    navigateToRegistrationScreen: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(title = {
                Text(text = stringResource(id = R.string.app_name))
            })
        }
    ) { innerPadding ->

        var emailFieldError by remember {
            mutableStateOf(false)
        }
        var emailFieldErrorCause by remember {
            mutableStateOf<String?>(null)
        }

        var isPasswordVisible by remember {
            mutableStateOf(false)
        }
        var passwordFieldError by remember {
            mutableStateOf(false)
        }
        var passwordFieldErrorCause by remember {
            mutableStateOf<String?>(null)
        }

        val context = LocalContext.current
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ElevatedCard(
                modifier = Modifier.padding(horizontal = 10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Login")
                    /**
                     * Email-ID Field
                     */
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = "Enter your Email-Id")
                        },
                        value = loginScreenViewModel.emailId,
                        onValueChange = { emailId ->
                            loginScreenViewModel.onEvent(EventsOnLoginScreen.OnEmailIdClick(emailId = emailId))
                        },
                        isError = emailFieldError,
                        supportingText = {
                            emailFieldErrorCause?.let {
                                Text(text = it)
                            }
                        }
                    )
                    VerticalSpace(desiredSpace = 10)
                    /**
                     * Password Field
                     */
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = "Enter your Password")
                        },
                        value = loginScreenViewModel.password,
                        onValueChange = { password ->
                            loginScreenViewModel.onEvent(
                                EventsOnLoginScreen.OnPasswordClick(
                                    password = password
                                )
                            )
                        },
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        isError = passwordFieldError,
                        supportingText = {
                            passwordFieldErrorCause?.let {
                                Text(text = it)
                            }
                        },
                        trailingIcon = {
                            val image =
                                if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                            IconButton(onClick = {
                                isPasswordVisible = !isPasswordVisible
                            }) {
                                Icon(
                                    imageVector = image,
                                    contentDescription = "Password Visibility"
                                )
                            }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        )
                    )
                    Button(onClick = {
                        /**
                         *  Validation logic
                         *  1st email-id
                         */
                        if (loginScreenViewModel.emailId.isNotEmpty()) {
                            if (Patterns.EMAIL_ADDRESS.matcher(loginScreenViewModel.emailId)
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
                         * Password Field Validation
                         */
                        if (loginScreenViewModel.password.isNotEmpty()) {
                            if (loginScreenViewModel.password.length in 6..15) {
                                passwordFieldError = false
                                passwordFieldErrorCause = null
                            } else {
                                passwordFieldError = true
                                passwordFieldErrorCause = "Length should in b/w 6..15"
                            }
                        } else {
                            passwordFieldError = true
                            passwordFieldErrorCause = "Please enter your password"
                        }

                        if (emailFieldError || passwordFieldError) {
                            return@Button
                        } else {
                            loginScreenViewModel.logIn(
                                onSuccess = {
                                    emailFieldError = false
                                    emailFieldErrorCause = null
                                    passwordFieldError = false
                                    passwordFieldErrorCause = null
                                    Toast.makeText(
                                        context,
                                        "Successfully login",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                onFailure = {
                                    emailFieldError = true
                                    emailFieldErrorCause = "Check Email ID"
                                    passwordFieldError = true
                                    passwordFieldErrorCause = "Check your Password"
                                    Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            )
                        }
                    })
                    {
                        Text(text = "Login")
                    }
                    /**
                     * If user don't have an account, then navigate
                     * him to registration screen. Where user can
                     * create account.
                     */
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Don't have an account!!")
                        TextButton(
                            onClick = navigateToRegistrationScreen
                        ) {
                            Text(text = "Create")
                        }
                    }
                }
            }
        }
    }
}