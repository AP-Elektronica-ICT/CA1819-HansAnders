﻿using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    public class Regio
    {
        public int Id { get; set; }
        public string naam { get; set; }
        public List<Locatie> locaties { get; set; }
        public int tijd { get; set; }
        public string plaats { get; set; }
        
     /*   public regio(string naam, List<Locatie> locaties, int tijd)
        {
            this.naam = naam;
            this.locaties = locaties;
            this.tijd = tijd;
        }*/
    }
}
