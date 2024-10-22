using System.Diagnostics;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Migrations.Operations;

public class Queries
{

    public static void NavigationProperty()
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

    public static void ManualIncludeCached()
    {
        MeasureTime("Manual Include Cached", () =>
        {
            var context = new Context();
            var cache = new Dictionary<int, Category>();
            var products = context.Products
                    .ToList();

            foreach (var product in products)
            {
                var categoryId = product.CategoryId;

                if (!cache.ContainsKey(categoryId))
                {
                    cache[categoryId] = context
                    .Categories
                    .Where(i => i.CategoryId == categoryId)
                    .First();

                }

                product.Category = cache[categoryId];

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

            var idsToRetrieve = products
                .Select(i => i.CategoryId)
                .Distinct()
                .ToList();

            var categories = context
                .Categories
                .Where(i => idsToRetrieve.Contains(i.CategoryId))
                .ToDictionary(i => i.CategoryId, i => i);

            foreach (var product in products)
            {
                product.Category = categories[product.CategoryId];
            }
        });

    }

    public static void ExplicitJoin()
    {

        MeasureTime("Explicit join", () =>
        {
            var context = new Context();
            var res = from product in context.Products
                      join category in context.Categories
                      on product.CategoryId equals category.CategoryId
                      select new
                      {
                          product = product,
                          category = category
                      };

            var list = res.ToList();
        });

    }

    public static void SQL()
    {
        MeasureTime("Explicit join", () =>
        {
            var context = new Context();
            var query = context.Database
                            .SqlQuery<ProductDto>(
                                $"""
                                SELECT 
                                    p.ProductId,
                                    p.Name,
                                    p.Price,
                                    c.CategoryId,
                                    c.Name as CategoryName
                                FROM 
                                Products p 

                                JOIN Categories c 
                                    ON p.CategoryId = c.CategoryId
                                """)
                            .ToList();
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

        Console.WriteLine($"{label}\t\t\t{ts.TotalMilliseconds}");
    }
}