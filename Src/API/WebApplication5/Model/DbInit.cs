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
                                new Puzzel(){Vraag = "wat wordt het meetst verkocht in starbucks", Antwoord = "drankje 3"},
                                new Puzzel(){Vraag = "hoe ver kan je geraken met een enkele trein(locatie)", Antwoord = "destinatie x"},
                                new Puzzel(){Vraag = "wa uur is locket x open", Antwoord = "5:50"},
                            },
                            lat = 51.217263f,
                            lng = 4.421034f

                        },
                        new Locatie(){
                            locatienaam = "kathdraal",

                            puzzels = new List<Puzzel> {
                                new Puzzel(){Vraag = "wat is beeld x", Antwoord = "5"},
                                new Puzzel(){Vraag = "wanneer is beeld x", Antwoord = "2"},
                                new Puzzel(){Vraag = "waarom is dit een ding", Antwoord = "nee"},
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
