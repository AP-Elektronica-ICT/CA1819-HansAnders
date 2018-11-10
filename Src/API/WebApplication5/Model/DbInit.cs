using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    public class DbInit
    {
        public static void Initialize(GameContext context)
        {
            context.Database.EnsureCreated();

            /*  context.Games.Add(
                  new Game() {  speelLocatie=}
              );*/

            context.User.Add(
                new User() { Name = "Jantje" }
            );
            context.Regios.Add(
               new Regio() {
                   naam = "antwerpen",
                   locaties = 
                   new List<Locatie> {
                        new Locatie(){
                            locatienaam = "antwerpen centraal",

                            puzzels = new List<Puzzel> {
                                new VraagPuzzel(){Omschrijving = "wat wordt het meetst verkocht in starbucks", antwoord = "drankje 3"},
                                new VraagPuzzel(){Omschrijving = "hoe ver kan je geraken met een enkele trein(locatie)", antwoord = "destinatie x"},
                                new VraagPuzzel(){Omschrijving = "wa uur is locket x open", antwoord = "5:50"},
                            },
                            lat = 51.217263f,
                            lng = 4.421034f

                        },
                        new Locatie(){
                            locatienaam = "kathdraal",

                            puzzels = new List<Puzzel> {
                                new VraagPuzzel(){Omschrijving = "wat is beeld x", antwoord = "5"},
                                new VraagPuzzel(){Omschrijving = "wanneer is beeld x", antwoord = "2"},
                                new VraagPuzzel(){Omschrijving = "waarom is dit een ding", antwoord = "nee"},
                            },
                            lat = 51.220214f,
                            lng = 4.402223f

                        }
                   },
                   tijd = 60 * 60 * 5
               }
           );
            context.SaveChanges();

        }
    }
}
