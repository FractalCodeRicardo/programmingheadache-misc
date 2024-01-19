package org.example;

public class Animal {
    private String name;
    private String type;
    private Double weight;
    private Double height;

    public Animal(String name, String type, double weight, double height) {
        this.name = name;
        this.type = type;
        this.weight = weight;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Double getWeight() {
        return weight;
    }

    public Double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return String.format("(Name=%s,Type=%s,Weight=%s,Height=%s)",
                this.getName(),
                this.getType(),
                this.getWeight().toString(),
                this.getHeight().toString());

    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Animal)) {
            return false;
        }

        var animal = (Animal) o;

        return this.getName().equals(animal.getName()) &&
                this.getType().equals(animal.getType());

    }
}
