package com.alex.habitsapp.feature.authentication.presentation.login.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alex.habitsapp.core.presentation.HabitButton
import com.alex.habitsapp.core.presentation.HabitPasswordTextfield
import com.alex.habitsapp.core.presentation.HabitTextfield
import com.alex.habitsapp.ui.theme.HabitsAppTheme

@Composable
fun LoginForm(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.background(Color.White, shape = RoundedCornerShape(20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Log in with Email",
            modifier = Modifier.padding(12.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.tertiary
        )

        Divider(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), color = MaterialTheme.colorScheme.background)
        HabitTextfield(
            value = "Email",
            onValueChange = {},
            placeholder = "Email",
            contentDescription = "Enter Email", //Se usa para testing pero es usado tambien para discapacitados
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
                .padding(horizontal = 20.dp),
            leadingIcon = Icons.Outlined.Email,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onAny = {

            }),
            errorMessage = null,
            isEnabled = true
        )

        HabitPasswordTextfield(
            value = "Password", onValueChange = {}, contentDescription = "Enter Password",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)
                .padding(horizontal = 20.dp),
            errorMessage = null,
            isEnabled = true,
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onAny = {

            })
        )

        HabitButton(
            text = "Log in",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            isEnabled = true
        ) {

        }

        TextButton(onClick = { /*TODO*/ }) {
            Text(
                text = "Forgot Password?",
                color = MaterialTheme.colorScheme.tertiary,
                textDecoration = TextDecoration.Underline
            )
        }

        TextButton(onClick = { }) {
            Text(
                text = buildAnnotatedString {
                    append("Don’t have an account? ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Sign up")
                    }
                },
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginFormPreview() {
    HabitsAppTheme{
        LoginForm()
    }
}