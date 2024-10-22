public class ProductDto
{
    public int ProductId { get; set; }
    public string Name { get; set; } = string.Empty;

    public decimal Price { get; set; }

    public int CategoryId { get; set; }
    public string CategoryName { get; set; } = string.Empty;
}