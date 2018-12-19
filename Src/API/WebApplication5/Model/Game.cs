using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    public class Game
    {
        public int id { get; set; }
        public int teamamount { get; set; }
        public List<Team> teams { get; set; }
        public List<Locatie> enabledLocaties { get; set; }
        public Regio regio { get; set; }
        public long starttijd { get; set; }
    }
}
