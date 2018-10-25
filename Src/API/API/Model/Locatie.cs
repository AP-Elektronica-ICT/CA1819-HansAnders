using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    class Locatie
    {
        string locatienaam;
        List<PuzzelVerzameling> puzzelverzamelingen;
        int time;

        public Locatie(string naam, List<PuzzelVerzameling> verzamelingen, int time)
        {
            this.locatienaam = naam;
            this.puzzelverzamelingen = verzamelingen;
            this.time = time;
        }
    }
}
