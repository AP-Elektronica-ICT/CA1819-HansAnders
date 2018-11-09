﻿using Microsoft.EntityFrameworkCore;
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
        public DbSet<regio> Regios { get; set; }
        public DbSet<VraagPuzzel> vraagPuzzels { get; set; }
        public DbSet<ARPuzzel> arPuzzels { get; set; }
    }
}