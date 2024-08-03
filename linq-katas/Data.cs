namespace LinqKatas;

public class Data
{

    public static IEnumerable<Animal> Get()
    {
        var animals = new List<Animal>
        {
            new (Name: "Lion", Type: "Mammal", Weight: 190.0, Height: 3.5),
            new (Name: "Elephant", Type: "Mammal", Weight: 5000.0, Height: 10.0),
            new (Name: "Penguin", Type: "Bird", Weight: 1.5, Height: 0.5),
            new (Name: "Snake", Type: "Reptile", Weight: 10.0, Height: 1.0),
            new (Name: "Dolphin", Type: "Mammal", Weight: 300.0, Height: 2.0),
            new (Name: "Eagle", Type: "Bird", Weight: 5.0, Height: 0.8),
            new (Name: "Tiger", Type: "Mammal", Weight: 250.0, Height: 3.0),
            new (Name: "Crocodile", Type: "Reptile", Weight: 150.0, Height: 2.5),
            new (Name: "Giraffe", Type: "Mammal", Weight: 1200.0, Height: 5.5),
            new (Name: "Hawk", Type: "Bird", Weight: 2.0, Height: 0.6),
            // Duplicated hawk
            new (Name: "Hawk", Type: "Bird", Weight: 2.5, Height: 0.6)
        };

        return animals;
    }
}