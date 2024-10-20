using Microsoft.EntityFrameworkCore;

public class Context : DbContext
{
    public DbSet<Product> Products { get; set; }
    public DbSet<Category> Categories { get; set; }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    {
        optionsBuilder.UseSqlite("Data Source=app.db"); 
    }
    

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {

    }
}