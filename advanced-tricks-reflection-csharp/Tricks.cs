using System.Linq.Expressions;
using System.Reflection;

namespace advanced_tricks_reflection_csharp;

public class Tricks
{


    public void getNameWithLambda()
    {

        var prop = GetPropertyInfo(new Foo(), i => i.Name);
        Console.WriteLine(prop.Name);

    }

    public static PropertyInfo GetPropertyInfo<TSource, TProperty>(
    TSource source,
    Expression<Func<TSource, TProperty>> propertyLambda)
    {
        if (propertyLambda.Body is not MemberExpression member)
        {
            throw new ArgumentException(string.Format(
                "Expression '{0}' refers to a method, not a property.",
                propertyLambda.ToString()));
        }

        if (member.Member is not PropertyInfo propInfo)
        {
            throw new ArgumentException(string.Format(
                "Expression '{0}' refers to a field, not a property.",
                propertyLambda.ToString()));
        }

        Type type = typeof(TSource);
        if (propInfo.ReflectedType != null && type != propInfo.ReflectedType && !type.IsSubclassOf(propInfo.ReflectedType))
        {
            throw new ArgumentException(string.Format(
                "Expression '{0}' refers to a property that is not from type {1}.",
                propertyLambda.ToString(),
                type));
        }

        return propInfo;
    }


    public void Mapper() {
        var foo = new Foo();
        var bar = Map<Foo, Bar>(foo);
    }

      public static TTarget Map<TSource, TTarget>(TSource source)
        where TTarget : new()
    {
        TTarget target = new TTarget();
        foreach (PropertyInfo sourceProperty in typeof(TSource).GetProperties())
        {
            PropertyInfo targetProperty = typeof(TTarget).GetProperty(sourceProperty.Name);
            if (targetProperty != null && targetProperty.CanWrite)
            {
                object value = sourceProperty.GetValue(source, null);
                targetProperty.SetValue(target, value, null);
            }
        }
        return target;
    }
}