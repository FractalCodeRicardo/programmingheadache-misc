 namespace advanced_tricks_reflection_csharp;

public class PrivateMethodClass 
{
    private void PrivateMethod()
    {
        Console.WriteLine("Private method called!");
    }

    private string PrivateMethodWithArgs(int number, string text)
    {
        return $"Called with arguments: {number} and {text}";
    }
}
