using Microsoft.EntityFrameworkCore.Metadata.Conventions;

public class Data
{

    public static void RemoveAll(Context context)
    {
        var categories = context.Categories.ToList();
        context.Categories.RemoveRange(categories);

        var products = context.Products.ToList();
        context.Products.RemoveRange(products);
        context.SaveChanges();

    }

    public static void CreateProducts(Context context, int num,List<Category> categories)
    {
        var products = new List<Product>();

        var j = 0;
        for (int i = 0; i < num; i++)
        {
            products.Add(new Product
            {
                Name = "Product " + i,
                CategoryId = categories[j++].CategoryId
            });

            if (j == categories.Count())
            {
                j = 0;
            }
        };

        context.AddRange(products);
        context.SaveChanges();
    }

    public static List<Category> CreateCategories(Context context, int num)
    {
        var categories = new List<Category>();

        for (int i = 0; i < num; i++)
        {
            categories.Add(new Category
            {
                Name = "Category " + i
            });
        };

        context.AddRange(categories);
        context.SaveChanges();

        return categories;
    }


    public static void InitDataBase(int numProducts, int numCategories)
    {
        var context = new Context();
        context.Database.EnsureCreated();

        RemoveAll(context);

        var categories = CreateCategories(context, numCategories);
        CreateProducts(context, numProducts, categories);
    }
}