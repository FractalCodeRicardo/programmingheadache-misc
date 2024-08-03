namespace LinqKatas;


public class Katas
{

    public static string? GetFirstBirdNameOrderByName(IEnumerable<Animal> animals)
    {
        return animals
        .Where(i => i.Type == "Bird")
        .Select(i => i.Name)
        .Order()
        .FirstOrDefault();
    }

    public static string? GetHeaviestAnimalName(IEnumerable<Animal> animals)
    {
        return animals
            .OrderByDescending(i => i.Weight)
            .Select(i => i.Name)
            .FirstOrDefault();
    }

    public static string? GetTheSmallestAnimalName(IEnumerable<Animal> animals)
    {
        return animals
            .OrderBy(i => i.Height)
            .Select(i => i.Name)
            .FirstOrDefault();
    }

    public static double GetAverageWeightOfMammals(IEnumerable<Animal> animals)
    {
        return animals
            .Where(i => i.Type == "Mammal")
            .Average(i => i.Weight);
    }

    public static IEnumerable<string> GetAllReptileNames(IEnumerable<Animal> animals)
    {
        return animals
            .Where(i => i.Type == "Reptile")
            .Select(i => i.Name);
    }
    public static string? GetTallestAnimalName(IEnumerable<Animal> animals)
    {
        return animals
            .OrderByDescending(i => i.Height)
            .Select(i => i.Name)
            .FirstOrDefault();
    }

    public static IEnumerable<string> GetNamesOfAnimalsHeavierThan(IEnumerable<Animal> animals, double weight)
    {
        return animals
            .Where(i => i.Weight > weight)
            .Select(i => i.Name);
    }

    public static IDictionary<string, int> GetCountOfEachAnimalType(IEnumerable<Animal> animals)
    {
        return animals
            .GroupBy(i => i.Type)
            .ToDictionary(g => g.Key, g => g.Count());
    }
}
