using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    class VraagPuzzel : Puzzel
    {
        public string antwoord { get; set; }
        public VraagPuzzel(string omschrijving, string antwoord): base(omschrijving)
        {
            this.antwoord = antwoord;

        }
    }
}
