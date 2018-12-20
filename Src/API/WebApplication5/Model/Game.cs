using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    public class Game
    {
        public int GameId { get; set; }
        public int teamamount { get; set; }
        public ICollection<Team> teams { get; set; }
        public ICollection<EnabledGameLocaties> enabledLocaties { get; set; }
        public Regio regio { get; set; }
        public int regioId { get; set; }
        public long starttijd { get; set; }
    }


    public class EnabledGameLocaties
    {
        public int GameId { get; set; }
        
        public Game game { get; set; }

        public int LocatieId { get; set; }
        public Locatie locatie { get; set; }
    }
}
