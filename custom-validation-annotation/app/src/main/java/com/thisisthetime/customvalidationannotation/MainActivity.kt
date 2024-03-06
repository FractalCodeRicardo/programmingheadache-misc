package com.thisisthetime.customvalidationannotation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.thisisthetime.customvalidationannotation.ui.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserScreen(UserViewModel())
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(viewModel: UserViewModel) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            label = { Text("Name") },
            value = state.name,
            onValueChange = {
                viewModel.updateProperty(state.copy(name = it))
            },
            isError = state.error?.property == "name"
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            label = { Text("Email") },
            value = state.email,
            onValueChange = {
                viewModel.updateProperty(state.copy(email = it))
            },
            isError = state.error?.property == "email"
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            label = { Text("Password") },
            value = state.password,
            onValueChange = {
                viewModel.updateProperty(state.copy(password = it))
            },
            isError = state.error?.property == "password"
        )

        Text(
            text = state.error?.message?:"",
            color =  MaterialTheme.colorScheme.error
        )

        Button(
            onClick = {
                viewModel.save()
            },
            content ={ Text("Save")}
        )


    }
}

class UserViewModel : ViewModel() {
    private val _state: MutableStateFlow<UserState> = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state

    fun updateProperty(newState: UserState) {
        _state.update { newState }
    }

    fun save() {
        val stateValidator = ValidateState(UserState::class);
        val error = stateValidator.validate(state.value)

        _state.update {
            it.copy(error = error)
        }
    }
}



data class UserState(

    @property:NotEmptyValidation()
    val name: String = "",

    @property:EmailValidation()
    val email: String = "",

    @property:PasswordValidation()
    val password: String = "",

    val error: ValidationError? = null // <- we add this

)

@Composable
@Preview
fun UserScreenPreview() {
    AppTheme {
        Surface {
            UserScreen(viewModel = UserViewModel())
        }
    }
}

@Target( AnnotationTarget.PROPERTY)
annotation class NotEmptyValidation()

@Target( AnnotationTarget.PROPERTY)
annotation class EmailValidation()

@Target( AnnotationTarget.PROPERTY)
annotation class PasswordValidation()

data class ValidationError(
    val property: String = "",
    val message: String = ""
)

class ValidateState<State: Any>(
    // we are going to use reflection so we pass the KClass instance
    val kClass: KClass<State>
) {
    fun validate(state: State): ValidationError? {
        kClass.memberProperties.forEach {
            if (it.annotations.isEmpty())
                return@forEach

            val annotation = it.annotations[0];
            val property = it.name
            val value = it.get(state)

            if (annotation is NotEmptyValidation) {
                if (isEmpty(value)) {
                    return ValidationError(property, "Must fill this field")
                }
            }

            if (annotation is EmailValidation) {
                if (isNotEmail(value)) {
                    return ValidationError(property, "Not valid email")
                }

            }

            if (annotation is PasswordValidation) {
                 if (isNotPassword(value)) {
                    return ValidationError(property, "Not valid password")
                }
            }

        }

        return null
    }

    private fun isEmpty(value: Any?): Boolean {
        return value.toString().isEmpty()
    }

    private fun isNotEmail(value: Any?): Boolean{
       return !Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
           .matches(value.toString())
    }

    private fun isNotPassword(value: Any?): Boolean{
        return !Regex("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{5,}$")
            .matches(value.toString());
    }

}