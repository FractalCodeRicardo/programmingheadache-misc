package com.thisisthetime.genericscomposableduplicatecode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thisisthetime.genericscomposableduplicatecode.ui.theme.AppTheme

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
                }
            }
        }
    }
}
data class Beer(
    val name: String = "",
    val alcoholByVolume: Double = 0.0,
    val price: Double = 0.0
)

data class Coffee(
    val name: String = "",
    val price: Double = 0.0,
    val extras: String = ""
)

data class Soda(
    val name: String = "",
    val price: Double = 0.0,
    val sugarGrams: Double = 0.0
)

val beerList = listOf(
    Beer(name = "IPA", alcoholByVolume = 6.5, price = 5.99),
    Beer(name = "Stout", alcoholByVolume = 7.2, price = 6.49),
    Beer(name = "Pale Ale", alcoholByVolume = 5.0, price = 4.99)
)

val coffeeList = listOf(
    Coffee(name = "Latte", price = 3.49, extras = "Whipped cream"),
    Coffee(name = "Espresso", price = 2.99, extras = "Double shot"),
    Coffee(name = "Cappuccino", price = 4.25, extras = "Cinnamon sprinkle")
)

val sodaList = listOf(
    Soda(name = "Cola", price = 1.99, sugarGrams = 39.0),
    Soda(name = "Lemon-Lime", price = 1.79, sugarGrams = 33.5),
    Soda(name = "Ginger Ale", price = 2.25, sugarGrams = 28.0)
)

@Composable
fun ListBeers(
    items: List<Beer>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        this.items(
            items = items,
            itemContent = {
                ElevatedCard(modifier = Modifier.padding(10.dp)) {
                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(
                            text = it.name,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Divider()

                        Column (
                            modifier = Modifier.padding(start = 20.dp)
                        ){
                            Text("Name: " + it.name)
                            Text("Alcohol: " + it.alcoholByVolume)
                            Text("Price " + it.price)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        )
    }
}

@Composable
fun ListCoffees(
    items: List<Coffee>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        this.items(
            items = items,
            itemContent = {
                ElevatedCard(
                    modifier = Modifier.padding(10.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(
                            text = it.name,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Divider()

                        Column (
                            modifier = Modifier.padding(start = 20.dp)
                        ){
                            Text("Name: " + it.name)
                            Text("Price: " + it.price)
                            Text("Extras: " + it.extras)
                        }

                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        )
    }
}

@Composable
fun ListSodas(
    items: List<Soda>,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        this.items(
            items = items,
            itemContent = {
                ElevatedCard(modifier = Modifier.padding(10.dp)) {
                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(
                            text = it.name,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Divider()

                        Column (
                            modifier = Modifier.padding(start = 20.dp)
                        ){
                            Text("Name: " + it.name)
                            Text("Price: " + it.price)
                            Text("Sugar Grams: " + it.sugarGrams)
                        }

                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        )
    }
}

@Composable
fun <T: Any> GenericList(
    items: List<T>,
    title: String,
    contentDetails: @Composable (T) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        this.items(
            items = items,
            itemContent = {
                ElevatedCard(modifier = Modifier.padding(10.dp)) {
                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(
                            text = title,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Divider()

                        Column (
                            modifier = Modifier.padding(start = 20.dp)
                        ){
                            contentDetails(it)
                        }

                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        )
    }
}

@Composable
fun NewBeersList(items: List<Beer>) {
    GenericList( items,"Beers",
        contentDetails = {
            Text("Name: " + it.name)
            Text("Alcohol: " + it.alcoholByVolume)
            Text("Price " + it.price)
        }
    )
}

@Composable
fun NewCoffeesList(items: List<Coffee>) {
    GenericList(items,"Coffees",
        contentDetails = {
            Text("Name: " + it.name)
            Text("Price: " + it.price)
            Text("Extras: " + it.extras)
        }
    )
}

@Composable
fun NewSodasList(items: List<Soda>) {
    GenericList(items,"Sodas",
        contentDetails = {
            Text("Name: " + it.name)
            Text("Price: " + it.price)
            Text("Sugar Grams: " + it.sugarGrams)
        }
    )
}


@Composable
@Preview
fun BeersPreview() {
    AppTheme {
        Surface {
            NewBeersList(items = beerList)
        }
    }
}

@Composable
@Preview
fun CoffeesPreview() {
    AppTheme {
        Surface {
            NewCoffeesList(items = coffeeList)
        }
    }
}

@Composable
@Preview
fun SodasPreview() {
    AppTheme {
        Surface {
            NewSodasList(items = sodaList)
        }
    }
}