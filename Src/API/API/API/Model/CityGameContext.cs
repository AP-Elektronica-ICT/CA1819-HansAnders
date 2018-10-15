using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace API.Model
{
    public class CityGameContext : DbContext
    {
        public CityGameContext(DbContextOptions<CityGameContext> options) : base(options)
        { }
            
            
            
    }
}
