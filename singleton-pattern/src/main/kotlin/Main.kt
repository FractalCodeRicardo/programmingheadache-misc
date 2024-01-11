data class User(val id: String)
data class Customer(val id: String)
data class Provider(val id: String)


// Users
interface UsersViewModel {
    fun showUsers()
}

class UsersViewModelImp : UsersViewModel {
    override fun showUsers() {
        println("users")
    }
}


// Customers
interface CustomersViewModel {
    fun showCostumers()
}

class CustomersViewModelImp : CustomersViewModel {
    override fun showCostumers() {
        println("costumer")
    }
}

// Providers
interface ProvidersViewModel {
    fun showProviders()
}

class ProvidersViewModelImp : ProvidersViewModel {
    override fun showProviders() {
        println("providers")
    }
}


//

object ViewModelFactory {
    val users: UsersViewModel = UsersViewModelImp()
    val customers: CustomersViewModel = CustomersViewModelImp()
    val providers: ProvidersViewModel = ProvidersViewModelImp()
}


class MapViewModelFactory {
    companion object {
        private val mapInstances = mutableMapOf<String, Any>()

        fun <T : Any> getInstance(key: String, create: () -> T): T {
            if (!mapInstances.containsKey(key)) {
                mapInstances[key] = create()
            }
            return mapInstances[key] as T
        }

        fun users(): UsersViewModel {
            return getInstance("users") { UsersViewModelImp() }
        }

        fun customers(): CustomersViewModel {
            return getInstance("customers") { CustomersViewModelImp() }
        }

        fun providers(): ProvidersViewModel {
            return getInstance("providers") { ProvidersViewModelImp() }
        }

    }
}


fun main(args: Array<String>) {

    val usersViewModel = MapViewModelFactory.users()
    usersViewModel.showUsers()

    val customersViewModel = MapViewModelFactory.customers()
    customersViewModel.showCostumers()

    val providersViewModel = MapViewModelFactory.providers()
    providersViewModel.showProviders()

}