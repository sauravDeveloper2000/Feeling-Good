package com.example.feelinggood.auth.ui.login_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
                        trailingIcon = {
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Default.VisibilityOff,
                                    contentDescription = "Password Visibility"
                                )
                            }
                        }
                    )
                    Button(onClick = { /*TODO*/ }) {
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