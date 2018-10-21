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
        float longitude;
        float latitude;

        public PuzzelVerzameling(string naam, List<Puzzel> puzzels, float longitude, float latitude)
        {
            this.naam = naam;
            this.puzzels = puzzels;
            this.longitude = longitude;
            this.latitude = latitude;
        }
    }
}
