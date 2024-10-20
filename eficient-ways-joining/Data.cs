using Microsoft.EntityFrameworkCore.Metadata.Conventions;

public class Data
{


    public static void Create()
    {
        var categories = new List<Category>();

        for (int i = 0; i < 100; i++)
        {
            categories.Add(new Category
            {
                Name = "Category " + i
            });
        };

        var context = new Context();
        context.Database.EnsureCreated();
        context.AddRange(categories);
        context.SaveChanges();

        var products = new List<Product>();

        var j = 0;
        for (int i = 0; i < 500000; i++)
        {
            products.Add(new Product
            {
                Name = "Product " + i,
                CategoryId = categories[j++].CategoryId
            });

            if (j == categories.Count()) {
                j = 0;
            }
        };

        context.AddRange(products);
        context.SaveChanges();
    }
}