using System.Linq.Expressions;

public static class ExpressionBuilder
{
    public static Func<T, bool> BuildPredicate<T>(string propertyName, object value)
    {
        // Get the type of the object
        Type type = typeof(T);
        
        // Get the property info for the specified property name
        var property = type.GetProperty(propertyName);
        if (property == null)
        {
            throw new ArgumentException($"Property '{propertyName}' not found on type '{type.Name}'");
        }

        // Create a parameter expression representing the input parameter (e.g., x => ...)
        var parameter = Expression.Parameter(type, "x");
        
        // Create an expression to access the property (e.g., x.PropertyName)
        var propertyAccess = Expression.Property(parameter, property);
        
        // Create a constant expression for the value to compare (e.g., ... == value)
        var constant = Expression.Constant(value);
        
        // Create a binary expression representing equality (e.g., x.PropertyName == value)
        var equality = Expression.Equal(propertyAccess, constant);
        
        // Combine the parameter and the equality expression into a lambda expression (e.g., x => x.PropertyName == value)
        var lambda = Expression.Lambda<Func<T, bool>>(equality, parameter);
        
        return lambda.Compile();
    }
}