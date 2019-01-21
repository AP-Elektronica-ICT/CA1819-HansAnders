using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    public class CaptureLocatie
    {
        public int id { get; set; }
        public int locatieId { get; set; }
        public Locatie locatie { get; set; }
        public int score { get; set; }
        
    }
}
