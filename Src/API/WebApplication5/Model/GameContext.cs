using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    public class GameContext : DbContext
    {
        public GameContext(DbContextOptions<GameContext> options) : base(options)
        {

        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<EnabledGameLocaties>()
            .HasKey(t => new { t.GameId, t.LocatieId });

            modelBuilder.Entity<EnabledGameLocaties>()
                .HasOne(pt => pt.game)
                .WithMany(p => p.enabledLocaties)
                .HasForeignKey(pt => pt.GameId);

            modelBuilder.Entity<EnabledGameLocaties>()
                .HasOne(pt => pt.locatie)
                .WithMany(t => t.enabledingames)
                .HasForeignKey(pt => pt.LocatieId);
        }

        public DbSet<Game> Games { get; set; }
        public DbSet<User> User { get; set; }
        public DbSet<Regio> Regios { get; set; }
        public DbSet<Locatie> locaties { get; set; }
        public DbSet<Puzzel> puzzels { get; set; }
        public DbSet<Team> Teams { get; set; }
    }
}
