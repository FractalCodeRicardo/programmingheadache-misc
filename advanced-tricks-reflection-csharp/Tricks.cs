using System.Linq.Expressions;
using System.Reflection;

namespace advanced_tricks_reflection_csharp;

public class Tricks
{


    public void GetNameWithLambda()
    {
        // I nstead of
        // var nameProp = "Name"; 
        // you can use 
        var nameProp = PropertyName((Foo i) => i.Name);
        Console.WriteLine(nameProp);
    }

    public static string PropertyName<Entity, Prop>(
        Expression<Func<Entity, Prop>> expression)
    {
        MemberExpression member = expression.Body as MemberExpression;

        return member.Member.Name;
    }


    public void EasyMapper()
    {
        var foo = new Foo();
        var bar = new Bar();

        var mapper = new Mapper<Bar, Foo>();
        mapper.Map(bar, foo);

        Console.WriteLine(foo.Name);
    }

    public void CallPrivateMethod()
    {
        var instance = new PrivateMethodClass();
        var type = typeof(PrivateMethodClass);

        var privateMethod = type.GetMethod("GetPrivateThings", BindingFlags.NonPublic | BindingFlags.Instance);

        // Invoke the private method
        privateMethod.Invoke(instance, null);

    }


    public void CollectionExtensions()
    {
        var dates = new List<Foo>() {
            new Foo() { Date = new DateTime(2024, 1, 1)},
            new Foo() { Date = new DateTime(2024, 1, 2)}
        };

        var from = new DateTime(2024, 1, 1);
        var to = new DateTime(2024, 1, 1);

        var filtered = dates.Between(x => x.Date, from, to);

        filtered.ToList().ForEach(x => Console.WriteLine(x.Date));
    }

    public void ComplexExpression()
    {
        var list = new List<Foo>() { 
            new Foo() { Bar = "varValue", Date = new DateTime(2024, 1, 1)}
        };

        var builder = new ExpressionBuilder<Foo>();
        
        var condition = new List<Tuple<string, object>>() {
            new Tuple<string, object>("Bar", "varValue"),
            new Tuple<string, object>("Date", new DateTime(2024, 1, 1))
        };

        var expression = builder.ComplexExpression(condition);
        var filtered = list.Where(expression).ToList();

        filtered.ForEach(x => Console.WriteLine(x.Bar));
    }

}