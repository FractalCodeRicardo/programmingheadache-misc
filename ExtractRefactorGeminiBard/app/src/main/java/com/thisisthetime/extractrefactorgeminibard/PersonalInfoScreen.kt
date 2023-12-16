package com.thisisthetime.extractrefactorgeminibard



import android.widget.Spinner
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp

// Additional imports for specific components used

import androidx.compose.material3.DatePicker
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.Modifier
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalInfoScreen(
    name: String = "",
    onNameChange: (String) -> Unit,
    lastName: String = "",
    onLastNameChange: (String) -> Unit,
    email: String = "",
    onEmailChange: (String) -> Unit,
    nationality: String = "",
    onNationalityChange: (String) -> Unit,
    birthDate: LocalDate? = null,
    onBirthDateChange: (LocalDate?) -> Unit,
    onSubmit: () -> Unit,
) {
    Surface {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Enter your personal information",
                style = MaterialTheme.typography.bodyLarge,
            )

            NameField(
                value = name,
                onValueChange = onNameChange,
            )

            LastNameField(
                value = lastName,
                onValueChange = onLastNameChange,
            )

            EmailField(
                value = email,
                onValueChange = onEmailChange,
            )

            NationalityField(
                value = nationality,
                onValueChange = onNationalityChange,
            )

            BirthdateField(
                birthDate = birthDate,
                onBirthDateChange = onBirthDateChange,
            )

            SubmitButton(
                onClick = onSubmit,
            )
        }
    }
}



@Composable
fun NameField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Name") },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
    )
}

@Composable
fun LastNameField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Last Name") },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
    )
}

@Composable
fun EmailField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Email") },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BirthdateField(
    modifier: Modifier = Modifier,
    birthDate: LocalDate? = null,
    onBirthDateChange: (LocalDate?) -> Unit,
) {
    DatePicker(
        state = DatePickerState(null, null, IntRange(1900, 2030), DisplayMode.Input) ,
        title = { Text("Birthdate") },
        modifier = modifier.fillMaxWidth()

    )
}

@Composable
fun SubmitButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
    ) {
        Text("Submit")
    }
}


@Composable
fun NationalityField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text("Nationality") },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
    )
}




@Preview(showBackground = true)
@Composable
fun PersonalInfoScreenPreview() {
    PersonalInfoScreen(
        name = "Jane Doe",
        lastName = "Johnson",
        email = "janedoe@example.com",
        nationality = "Canada",
        birthDate = null,
        onNameChange = { /* Handle name change here */ },
        onLastNameChange = { /* Handle last name change here */ },
        onEmailChange = { /* Handle email change here */ },
        onNationalityChange = { /* Handle nationality change here */ },
        onBirthDateChange = { /* Handle birth date change here */ },
        onSubmit = { /* Handle submit action here */ },
    )
}