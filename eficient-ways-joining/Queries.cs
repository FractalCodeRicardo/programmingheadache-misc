using System.Diagnostics;
using Microsoft.EntityFrameworkCore;

public class Queries
{

    public static void IncludeProducts()
    {
        MeasureTime("Include", () =>
        {
            var context = new Context();
            var products = context.Products
                    .Include(i => i.Category)
                    .ToList();
        });

    }

    public static void ManualInclude()
    {
        MeasureTime("Manual Include", () =>
        {
            var context = new Context();
            var products = context.Products
                    .ToList();

            foreach (var product in products)
            {
                product.Category = context
                    .Categories
                    .FirstOrDefault(i => i.CategoryId == product.CategoryId);
            }
        });

    }

        public static void ManualIncludeInClause()
    {
        MeasureTime("Manual Include In", () =>
        {
            var context = new Context();
            var products = context.Products
                    .ToList();

            var ids = products
                .Select(i => i.CategoryId)
                .Distinct()
                .ToList();

            var categories = context
                .Categories
                .Where(i => ids.Contains(i.CategoryId))
                .ToDictionary(i => i.CategoryId, i => i);

            foreach (var product in products)
            {
                product.Category = categories[product.CategoryId];
            }
        });

    }

    public static void MeasureTime(string label, Action action)
    {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.Reset();
        stopwatch.Start();

        action();

        stopwatch.Stop();

        TimeSpan ts = stopwatch.Elapsed;

        Console.WriteLine($"{label}\t\t{ts.TotalMilliseconds}");
    }
}