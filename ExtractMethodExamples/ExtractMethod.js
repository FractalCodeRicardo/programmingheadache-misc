
// Isolate calculation for better understanding
function calculateTotal(sales) {

    let grandTotal = 0;

    for (let sale of sales) {
        var total = (sale.price * sale.quantity) + //subtotal
            (sale.price * sale.quantity) * sale.taxRate; // taxes

        grandTotal += total;
    }

    return grandTotal
}


function calculateTotal(sale) {
    var subtotal = sale.price * sale.quantity;
    var taxes = subtotal * sale.taxRate;
    var total = subtotal + taxes;

    return total;
}


function calculateTotalSales(sales) {
    return sales.reduce((a, i) => a + calculateTotal(i))
    let grandTotal = 0;

    for (let sale of sales) {
        grandTotal += calculateTotal(sale);
    }

    return grandTotal;
}




// Replace big conditions for a function with a proper name

function customersWithPremiumDiscount(customers) {
    let customersWithDiscounts = [];

    customers.forEach(customer => {

        if (
            customer.age > 18 &&
            (customer.membership === 'Gold' || customer.membership === 'Platinum') &&
            customer.totalPurchases > 1000 &&
            !customer.hasPendingReturns
        ) {
            customersWithDiscounts.push(customer);
        }

    });

    return customersWithDiscounts;
}



function HasDiscount(customer) {
    let isGoldOrPremium = customer.membership === 'Gold' 
        || customer.membership === 'Platinum';

    return customer.age > 18 &&
        isGoldOrPremium &&
        customer.totalPurchases > 1000 &&
        !customer.hasPendingReturns
}


function customersWithPremiumDiscount(customers) {
    return customers.filter(c => HasDiscount(c));
}


// replace object initialization with repeated code

function getPlayers() {
    let player1 = {
        name: "Archer",
        class: "Ranger",
        health: Defaults.Health,
        mana: Defaults.Mana,
        level: Defaults.Leve
    };
    
    let player2 = {
        name: "Mage",
        class: "Wizard",
        health: Defaults.Health,
        mana: Defaults.Mana,
        level: Defaults.Leve
    };
    
    let player3 = {
        name: "Warrior",
        class: "Fighter",
        health: Defaults.Health,
        mana: Defaults.Mana,
        level: Defaults.Leve
    };

    return [player1, player2, player3]
}


function getPlayers() {
    return [
        createPlayer("Archer", "Ranger"),
        createPlayer("Mage", "Wizard"),
        createPlayer("Warrior", "Fighter")
    ]
}

function createPlayer(name, playerClass) {
    return {
        name: name,
        class: playerClass,
        health: Defaults.Health,
        mana: Defaults.Mana,
        level: Defaults.Leve
    };
}


// Extract similar loop into a method with the appropiated parametes
function saveHighValueOrders(orders) {
    for (let order of orders) {
        if (order.amount > 500) {
            saveOrder(order);
        }
    }
}

function saveVipOrders(orders) {
    for (let order of orders) {
        if (order.customerType === "VIP") {
            saveOrder(order);
        }
    }
}

function saveExpeditedOrders(orders) {
    for (let order of orders) {
        if (order.isExpedited) {
            saveOrder(order);
        }
    }
}

function saveOrders(orders, condition) {
    for (let order of orders) {
        if (condition(order)) {
            saveOrder(order);
        }
    }
}

function saveHighValueOrders(orders) {
    saveOrders(orders, order => order.amount > 500);
}

function saveVipOrders(orders) {
    saveOrders(orders, order => order.customerType === "VIP");
}

function saveExpeditedOrders(orders) {
    saveOrders(orders, order => order.isExpedited);
}


