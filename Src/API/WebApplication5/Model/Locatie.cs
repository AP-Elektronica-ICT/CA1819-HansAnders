using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    public class Locatie
    {
        public int Id { get; set; }
        public string locatienaam { get; set; }
        public ICollection<Puzzel> puzzels { get; set; }
        public float lng { get; set; }
        public float lat { get; set; }
        public ICollection<EnabledGameLocaties> enabledingames { get; set; }

     /*   public Locatie(string naam, List<Puzzel> puzzels, float lng, float lat)
        {
            this.locatienaam = naam;
            this.puzzels = puzzels;
            this.lng = lng;
            this.lat = lat;
        }*/
    }
}
