namespace LinqKatas;

[TestClass]
public class KatasTest
{
    
 private IEnumerable<Animal> animals;

    [TestInitialize]
    public void Initialize()
    {
        animals = Data.Get();
    }

    [TestMethod]
    public void GetFirstBirdNameOrderByName_ShouldReturn_Eagle()
    {
        var result = Katas.GetFirstBirdNameOrderByName(animals);
        Assert.AreEqual("Eagle", result);
    }

    [TestMethod]
    public void GetHeaviestAnimalName_ShouldReturn_Elephant()
    {
        var result = Katas.GetHeaviestAnimalName(animals);
        Assert.AreEqual("Elephant", result);
    }

    [TestMethod]
    public void GetTheSmallestAnimalName_ShouldReturn_Penguin()
    {
        var result = Katas.GetTheSmallestAnimalName(animals);
        Assert.AreEqual("Penguin", result);
    }

    [TestMethod]
    public void GetAverageWeightOfMammals_ShouldReturn_1488_33()
    {
        var result = Katas.GetAverageWeightOfMammals(animals);
        Assert.AreEqual(1488.33, result, 0.01);
    }

    [TestMethod]
    public void GetAllReptileNames_ShouldReturn_SnakeAndCrocodile()
    {
        var result = Katas.GetAllReptileNames(animals).ToList();
        CollectionAssert.Contains(result, "Snake");
        CollectionAssert.Contains(result, "Crocodile");
    }

    [TestMethod]
    public void GetTallestAnimalName_ShouldReturn_Elephant()
    {
        var result = Katas.GetTallestAnimalName(animals);
        Assert.AreEqual("Elephant", result);
    }

    [TestMethod]
    public void GetNamesOfAnimalsHeavierThan_ShouldReturnAnimals_HeavierThan300()
    {
        var result = Katas.GetNamesOfAnimalsHeavierThan(animals, 300).ToList();
        var expected = new List<string> { "Elephant", "Giraffe" };
        CollectionAssert.AreEqual(expected, result);
    }

    [TestMethod]
    public void GetCountOfEachAnimalType_ShouldReturn_CorrectCounts()
    {
        var result = Katas.GetCountOfEachAnimalType(animals);
        Assert.AreEqual(4, result["Bird"]);
        Assert.AreEqual(5, result["Mammal"]);
        Assert.AreEqual(2, result["Reptile"]);
    }
}