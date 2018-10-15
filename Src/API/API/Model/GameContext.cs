using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    class GameContext : DbContext
    {
        public GameContext(DbContextOptions<GameContext> options):base(options)
        {

        }

        protected override void OnModelCreating(ModelBuilder modelbuilder)
        { }

        public DbSet<Game> Games { get; set; }
        public DbSet<User> Users { get; set; }
        public DbSet<Puzzel> Puzzels { get; set; }
    }
}
