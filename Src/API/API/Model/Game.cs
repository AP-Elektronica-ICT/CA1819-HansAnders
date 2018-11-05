using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    public class Game
    {
        public int ID { get; set; }
        public int teamamount { get; set; }
        public List<Team> Teams { get; set; }
        public regio speelLocatie { get; set; }
        public int starttijd { get; set; }
    }
}
