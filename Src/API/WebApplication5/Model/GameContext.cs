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

        protected override void OnModelCreating(ModelBuilder modelbuilder)
        { }

        public DbSet<Game> Games { get; set; }
        public DbSet<User> User { get; set; }
        public DbSet<Regio> Regios { get; set; }
        public DbSet<Locatie> locaties { get; set; }
        public DbSet<Puzzel> puzzels { get; set; }
        public DbSet<Team> Teams { get; set; }
    }
}
