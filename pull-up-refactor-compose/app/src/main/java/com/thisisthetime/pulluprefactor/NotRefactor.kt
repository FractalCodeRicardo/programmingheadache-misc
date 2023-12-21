package com.thisisthetime.pulluprefactor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thisisthetime.pulluprefactor.ui.theme.PullUpRefactorTheme

data class Client(
    val name: String = "",
    val email: String = "",
    val phone: String = ""
)

data class Order(
    val productName: String = "",
    val quantity: Int = 0
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ClientScreen(client: Client) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .clickable { }
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.onPrimary

            )

            // App name in the center
            Text(
                text = "My app",
                color = MaterialTheme.colorScheme.onPrimary
            )

            // Hamburger menu icon
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                modifier = Modifier
                    .clickable { }
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        // Name field
        OutlinedTextField(
            value = client.name,
            onValueChange = { },
            label = { Text("Name") },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Email field
        OutlinedTextField(
            value = client.email,
            onValueChange = { },
            label = { Text("Email") },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    // Handle the "Next" action
                }
            )
        )

        // Phone field
        OutlinedTextField(
            value = client.phone,
            onValueChange = { },
            label = { Text("Phone") },
            leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Handle the "Done" action
                    // For example, submit the form
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Submit button
        Button(
            onClick = {
                // Handle form submission
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Submit")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderScreen(order: Order) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Back button
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .clickable { }
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.onPrimary

            )

            // App name in the center
            Text(
                text = "My app",
                color = MaterialTheme.colorScheme.onPrimary
            )

            // Hamburger menu icon
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = null,
                modifier = Modifier
                    .clickable { }
                    .size(24.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        // Product Name field
        OutlinedTextField(
            value = order.productName,
            onValueChange = {  },
            label = { Text("Product Name") },
            leadingIcon = { Icon(imageVector = Icons.Default.Create, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Quantity field
        OutlinedTextField(
            value = order.quantity.toString(),
            onValueChange = {},
            label = { Text("Quantity") },
            leadingIcon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    // Handle the "Done" action
                    // For example, submit the order
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Submit button
        Button(
            onClick = {
                // Handle order submission
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Submit Order")
        }
    }
}

@Composable
@Preview
fun ClientScreenPreview() {
    PullUpRefactorTheme {
        Surface {
            ClientScreen(
                Client(
                name = "Foo",
                email = "bar@email.com",
                phone = "+52777777"
            )
            )
        }
    }
}

@Composable
@Preview
fun OrderScreenPreview() {
    PullUpRefactorTheme {
        Surface {
            OrderScreen(order = Order(
                productName = "Foo product",
                quantity = 13
            ))
        }
    }
}