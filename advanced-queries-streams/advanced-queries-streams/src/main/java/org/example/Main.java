package org.example;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static Stream<Animal> animals() {
        List<Animal> animals = Arrays.asList(
                new Animal("Lion", "Mammal", 190.0, 3.5),
                new Animal("Elephant", "Mammal", 5000.0, 10.0),
                new Animal("Penguin", "Bird", 1.5, 0.5),
                new Animal("Snake", "Reptile", 10.0, 1.0),
                new Animal("Dolphin", "Mammal", 300.0, 2.0),
                new Animal("Eagle", "Bird", 5.0, 0.8),
                new Animal("Tiger", "Mammal", 250.0, 3.0),
                new Animal("Crocodile", "Reptile", 150.0, 2.5),
                new Animal("Giraffe", "Mammal", 1200.0, 5.5),
                new Animal("Hawk", "Bird", 2.0, 0.6),
                // Duplicated hawk
                new Animal("Hawk", "Bird", 2.5, 0.6)
        );

        return animals.stream();
    }

    public static void main(String[] args) {

        println("1. Iterate over the stream printing the names");
        // 1. Iterate over the stream printing the names
        animals().forEach(i -> println(i.getName()));

    //        Output:
    //        Lion
    //        Elephant
    //        Penguin
    //        Snake
    //        Dolphin
    //        Eagle
    //        Tiger
    //        Crocodile
    //        Giraffe
    //        Hawk
    //        Hawk


        println("\n2. Filter the birds to get only the birds");
        // 2. Filter the birds to get only the birds
        var birds = animals().filter(i -> i.getType().equals("Bird"));
        printAnimals(birds);
        // Output:
        // Penguin Eagle Hawk Hawk


        println("\n3. Find the first Hawk");
        // 3. Find the first Hawk
        var hawk = animals()
                .filter(i -> i.getName().equals("Hawk"))
                .findFirst();

        if (hawk.isPresent()) {
            print(hawk.get());
        }
        // Output:
        // (Name=Hawk,Type=Bird,Weight=2.0,Height=0.6)


        println("\n4. Get the animals sorted by height");
        // 4. Get the animals sorted by height
        var smallestAnimalsFirst = animals()
                .sorted(Comparator.comparingDouble(i -> i.getHeight()));

        smallestAnimalsFirst.forEach(i -> print(String.format("%s(%s) ", i.getName(), i.getHeight())));
        // Output:
        // Penguin(0.5) Hawk(0.6) Hawk(0.6) Eagle(0.8) Snake(1.0) Dolphin(2.0) Crocodile(2.5) Tiger(3.0) Lion(3.5) Giraffe(5.5) Elephant(10.0)


        println("\n5. Map the stream to a new stream with only names and types");
        // 5. Map the stream to a new stream with only names and types
        var justNamesAndTypes = animals().map(i -> new Object() {
            String name = i.getName();
            String type = i.getType();
        });

        justNamesAndTypes.forEach(i -> print(String.format("%s/%s ", i.name, i.type)));
        // Output:
        // Lion/Mammal Elephant/Mammal Penguin/Bird Snake/Reptile Dolphin/Mammal Eagle/Bird Tiger/Mammal Crocodile/Reptile Giraffe/Mammal Hawk/Bird Hawk/Bird


        println("\n6. Get the smallest animal.");
        // 6. Get the smallest animal
        var smallestAnimal = animals()
                .min(Comparator.comparingDouble(i -> i.getHeight()));

        if (smallestAnimal.isPresent()) {
            print(smallestAnimal.get());
        }
        // Output
        // (Name=Penguin,Type=Bird,Weight=1.5,Height=0.5)


        println("\n7. Get the biggest animal");
        // 7. Get the biggest animal
        var biggestAnimal = animals()
                .max(Comparator.comparingDouble(i -> i.getHeight()));

        if (biggestAnimal.isPresent()) {
            print(biggestAnimal.get());
        }
        // Output
        // (Name=Elephant,Type=Mammal,Weight=5000.0,Height=10.0)


        println("\n8. Group animals by type");
        // 8. Group animals by type
        var groupsByType = animals().
                collect(Collectors.groupingBy(i -> i.getType()));

        print(groupsByType);
//        Output:
//        Mammal
//        Lion Elephant Dolphin Tiger Giraffe
//        Reptile
//        Snake Crocodile
//        Bird
//        Penguin Eagle Hawk Hawk


        println("\n9. Sum the heights of all animals");
        // 9. Sum the heights of all animals
        var sumHeight = animals().mapToDouble(i -> i.getHeight()).sum();
        print(sumHeight);

        //Output:
        // 30.0


        println("\n10. Remove hawks duplicated");
        // 10. Remove hawks duplicated
        var removedDuplicated = animals()
                .filter(i -> i.getName().equals("Hawk"))
                .distinct();

        print(removedDuplicated.findFirst().get());
        // Output:
        // (Name=Hawk,Type=Bird,Weight=2.0,Height=0.6)


        // 11. Get name of smallest mammal or nothing
        println("\n 11. Get name of smallest mammal or null");
        var smallestMammal = animals()
                .filter(i -> i.getType().equals("Mammal"))
                .sorted(Comparator.comparingDouble(i -> i.getHeight()))
                .map(i -> i.getName())
                .findFirst();

        if (smallestAnimal.isPresent()) {
            print(smallestMammal.get());
        }
        // Output:
        // Dolphin


        // 12. Get the smallest animal of each type
        println("\n 12. Get the smallest animal of each type ");

        var smallestOfGroups = animals()
                .collect(Collectors.groupingBy(i -> i.getType()))
                .entrySet()
                .stream()
                .map(i -> new Object() {
                    String group = i.getKey();
                    String smallest = i.getValue()
                            .stream()
                            .min(Comparator.comparingDouble(j -> j.getHeight()))
                            .get()
                            .getName();
                });

        smallestOfGroups
                .forEach(i -> println(String.format("%s: %s ", i.group, i.smallest)));

//       Output:
//        Mammal: Dolphin
//        Reptile: Snake
//        Bird: Penguin


        println(" 13. Get the sum height of each group");
        // 13. Get the sum height of each group
        var sumEachGroup = animals()
                .collect(Collectors.groupingBy(i -> i.getType()))
                .entrySet()
                .stream()
                .map(i -> new Object() {
                    String group = i.getKey();
                    Double sum = i.getValue()
                            .stream()
                            .mapToDouble(j -> j.getHeight())
                            .sum();
                });

        sumEachGroup
                .forEach(i -> println(String.format("%s %s ", i.group, i.sum.toString())));

//        Output:
//        Mammal 24.0
//        Reptile 3.5
//        Bird 2.5


        //14. Get the animals that contains 'in' in the name sorted by height
        println("\n 14. Get the animals that contains 'in' in the name sorted by height");

        var animalsWithIn = animals()
                .filter(i -> i.getName().toLowerCase().contains("in"))
                .sorted(Comparator.comparingDouble(i -> i.getHeight()));

        printAnimals(animalsWithIn);

        // Output:
        // Penguin Dolphin


        // 15. Get the names of the mammals with weight over 100
        println("\n 15. Get the names of the mammals with weight over 100");
        var namesMammalsOver100 = animals()
                .filter(i -> i.getType().equals("Mammal"))
                .filter(i -> i.getWeight() > 100.0)
                .map(i -> i.getName());

        printStrings(namesMammalsOver100);

        // Output:
        // Lion Elephant Dolphin Tiger Giraffe
    }

    public static void printAnimals(Stream<Animal> animals) {
        animals.forEach(i -> print(i.getName() + " "));
    }

    public static void printStrings(Stream<String> params) {
        params.forEach(i -> print(i + " "));
    }

    public static void print(List<Animal> animals) {
        animals.forEach(i -> print(i.getName() + " "));
    }

    public static void print(Map<String, List<Animal>> animals) {
        animals.forEach((key, value) -> {
            print(key + "\n");
            print(value);
            print("\n");
        });
    }

    public static void print(String param) {
        System.out.print(param);
    }

    public static void println(String param) {
        System.out.println(param);
    }

    public static <T> void print(T object) {
        print(object.toString());

    }
}