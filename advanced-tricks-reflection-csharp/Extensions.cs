namespace advanced_tricks_reflection_csharp;

public static class CollectionExtensions
{
    public static IEnumerable<T> Between<T>(
        this IEnumerable<T> source, 
        Func<T, DateTime> getDate, 
        DateTime from,
        DateTime to)
    {

        return source.Where(entity => 
            getDate(entity) >= from && 
            getDate(entity) <= to);
    }
}