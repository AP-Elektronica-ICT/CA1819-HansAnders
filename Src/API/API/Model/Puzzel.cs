using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    abstract class Puzzel
    {
        int ID;
        string Omschrijving;

        public Puzzel(string omschrijving)
        {
            this.Omschrijving = omschrijving;
        }
    }
}
