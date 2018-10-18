using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    class PuzzelVerzameling
    {
        int ID;
        string naam;
        List<Puzzel> puzzels;

        public PuzzelVerzameling(string naam, List<Puzzel> puzzels)
        {
            this.naam = naam;
            this.puzzels = puzzels;
        }
    }
}
