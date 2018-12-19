using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    public class Puzzel
    {
        public int id { get; set; }
        public string Vraag { get; set; }
        public string Antwoord { get; set; }
        public bool juist { get; set; }
       /*   public Puzzel(string omschrijving)
          {
              this.Omschrijving = omschrijving;
          }*/
    }
}
