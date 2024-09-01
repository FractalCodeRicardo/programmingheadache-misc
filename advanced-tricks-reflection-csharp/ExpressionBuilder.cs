using System.Linq.Expressions;

namespace advanced_tricks_reflection_csharp;

public class ExpressionBuilder<T>
{

    // creates expresion entity => entity.propertyName == value

    public Expression CreatePropertyExpression(Expression entityParameter, string propertyName, object? value)
    {
        var property = typeof(T).GetProperty(propertyName);
        var propertyAccess = Expression.MakeMemberAccess(entityParameter, property);
        var valueExpression = Expression.Constant(value, property.PropertyType);
        var equalsExpression = Expression.Equal(propertyAccess, valueExpression);

        return equalsExpression;
    }

    public Func<T, bool> ComplexExpression(
        List<Tuple<string, object>> conditions
        )
    {
        Expression expression = null;
        var parameter = Expression.Parameter(typeof(T), "entity");

        foreach (var condition in conditions)
        {
            var currentExpression = CreatePropertyExpression(parameter, condition.Item1, condition.Item2);

            if (expression == null)
            {
                expression = currentExpression;
                continue;
            }

            expression = Expression.AndAlso(expression, currentExpression);
        }

        var lambda = Expression.Lambda<Func<T, bool>>(expression, parameter);

        return lambda.Compile();
    }



}