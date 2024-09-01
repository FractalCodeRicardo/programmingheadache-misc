using System.Reflection;

namespace advanced_tricks_reflection_csharp;

public class Mapper<TSource, TTarget>
    where TSource : class
    where TTarget : class
{
    public void Map(TSource source, TTarget target)
    {
        var typeSource = typeof(TSource);

        var properties = typeSource.GetProperties();

        // loop each property
        foreach (var sourceProperty in properties)
        {
            CopyValues(source, sourceProperty, target);
        }
    }

    public void CopyValues(TSource source, PropertyInfo sourceProperty, TTarget target)
    {
        var typeTarget = typeof(TTarget);
        var targetProperty = typeTarget.GetProperty(sourceProperty.Name);

        if (targetProperty == null) return;

        object value = sourceProperty.GetValue(source, null);
        targetProperty.SetValue(target, value, null);
    }
}