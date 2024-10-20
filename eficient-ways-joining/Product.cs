using System.ComponentModel.DataAnnotations.Schema;

public class Product
{
    public int ProductId { get; set; }
    public string Name { get; set; } = string.Empty;
    public decimal Price { get; set; }

    public int CategoryId { get; set; }

    [ForeignKey("CategoryId")]
    public Category Category { get; set; }
}
