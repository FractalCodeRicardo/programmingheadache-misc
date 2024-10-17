using Microsoft.EntityFrameworkCore;

public class Context : DbContext
{
    public DbSet<Product> Products { get; set; }
    public DbSet<Category> Categories { get; set; }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    {
        optionsBuilder.UseSqlServer("database=EficientWays;server=ricardopc\\sqldev;user=sa;password=Desarrollo01.;TrustServerCertificate=True");
    }
    

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        // Configure relationships, keys, etc.
        modelBuilder.Entity<Product>()
            .HasOne<Category>()
            .WithMany()
            .HasForeignKey(p => p.CategoryId)
            .OnDelete(DeleteBehavior.NoAction);
    }
}