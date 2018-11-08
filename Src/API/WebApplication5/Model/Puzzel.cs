using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    public abstract class Puzzel
    {
        public int ID { get; set; }
        public string Omschrijving { get; set; }

      /*  public Puzzel(string omschrijving)
        {
            this.Omschrijving = omschrijving;
        }*/
    }
}
