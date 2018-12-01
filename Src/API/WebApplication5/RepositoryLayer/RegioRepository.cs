using System;
using System.Collections.Generic;
using System.Text;
using Model;
using System.Linq;

namespace RepositoryLayer
{
    public class RegioRepository
    {
        private GameContext _context;
        public RegioRepository(GameContext gameContext) {
            _context = gameContext;
        }
        public bool addGame(Regio regio)
        {
            if (regio != null)
            {
                _context.Regios.Add(regio);
                _context.SaveChanges();
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}
