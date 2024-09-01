namespace advanced_tricks_reflection_csharp;

public class Mapper<TSource, TTarget> 
    where TSource: class 
    where TTarget: class
{
    public void Map(TSource source, TTarget target)
    {
        var typeSource = typeof(TSource);
        var typeTarget = typeof(TTarget);
        foreach (var sourceProperty in typeof(TSource).GetProperties())
        {
            var targetProperty = typeTarget.GetProperty(sourceProperty.Name);

            if (targetProperty != null)
            {
                object value = sourceProperty.GetValue(source, null);
                targetProperty.SetValue(target, value, null);
            }
        }
    }
}