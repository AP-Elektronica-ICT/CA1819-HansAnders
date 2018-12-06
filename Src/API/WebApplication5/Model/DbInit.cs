using System;
using System.Collections.Generic;
using System.Text;
using System.Linq;

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

            if (context.Regios.Any())
            {
                context.User.Add(
                    new User() {
                        Name = "Jantje"
                    }
                );

                context.Games.Add(
                    new Game()
                    {
                        teamamount = 4,
                        starttijd = 1500,
                        regio = new Regio()
                        {
                            plaats = "Antwerpen",
                            naam = "Antwerpen",
                            locaties =
                            new List<Locatie> {
                                new Locatie(){
                                    locatienaam = "antwerpen centraal",
                                    puzzels = new List<Puzzel> {
                                        new Puzzel(){Vraag = "wat wordt het meetst verkocht in starbucks", Antwoord = "drankje 3"},
                                        new Puzzel(){Vraag = "hoe ver kan je geraken met een enkele trein(locatie)", Antwoord = "destinatie x"},
                                        new Puzzel(){Vraag = "wa uur is locket x open", Antwoord = "5:50"},
                                    },
                                    lat = 55.217263f,
                                    lng = 6.421034f
                                }
                            },
                            tijd = 60 * 60 * 5
                        },
                        Teams = new List<Team>() {
                        new Team()
                        {
                            Users = new List<User>
                            {
                                new User()
                                {
                                    Name = "brent",
                                    lat = 5,
                                    lng = 5
                                },
                                new User()
                                {
                                    Name = "yorick",
                                    lat = 5,
                                    lng = 5
                                },
                                new User()
                                {
                                    Name = "ruben",
                                    lat = 5,
                                    lng = 5
                                },
                            },
                            CapturedLocaties = new List<Locatie>
                            {
                                new Locatie()
                                {
                                    locatienaam = "antwerpen centraal",
                                    puzzels = new List<Puzzel> {
                                        new Puzzel(){Vraag = "wat wordt het meetst verkocht in starbucks", Antwoord = "drankje 3"},
                                        new Puzzel(){Vraag = "hoe ver kan je geraken met een enkele trein(locatie)", Antwoord = "destinatie x"},
                                        new Puzzel(){Vraag = "wa uur is locket x open", Antwoord = "5:50"},
                                    },
                                    lat = 55.217263f,
                                    lng = 6.421034f
                                }
                            },
                            TeamName = "teamHansAnders"
                        },
                        new Team()
                        {

                        }
                        },
                        EnabledLocaties = new List<Locatie> {
                        new Locatie()
                        {
                            locatienaam = "antwerpen centraal",

                            puzzels = new List<Puzzel> {
                                new Puzzel(){Vraag = "wat wordt het meetst verkocht in starbucks", Antwoord = "drankje 3"},
                                new Puzzel(){Vraag = "hoe ver kan je geraken met een enkele trein(locatie)", Antwoord = "destinatie x"},
                                new Puzzel(){Vraag = "wa uur is locket x open", Antwoord = "5:50"},
                            },
                            lat = 55.217263f,
                            lng = 6.421034f
                        }
                        }
                    }
                );

                context.Regios.Add(
                   new Regio()
                   {
                       naam = "Antwerpen",
                       locaties =
                       new List<Locatie> {
                        new Locatie(){
                            locatienaam = "antwerpen centraal",

                            puzzels = new List<Puzzel> {
                                new Puzzel(){Vraag = "wat wordt het meetst verkocht in starbucks", Antwoord = "drankje 3"},
                                new Puzzel(){Vraag = "hoe ver kan je geraken met een enkele trein(locatie)", Antwoord = "destinatie x"},
                                new Puzzel(){Vraag = "wa uur is locket x open", Antwoord = "5:50"},
                            },
                            lat = 55.217263f,
                            lng = 6.421034f
                        },
                        new Locatie(){
                            locatienaam = "kathdraal",

                            puzzels = new List<Puzzel> {
                                new Puzzel(){Vraag = "wat is beeld x", Antwoord = "5"},
                                new Puzzel(){Vraag = "wanneer is beeld x", Antwoord = "2"},
                                new Puzzel(){Vraag = "waarom is dit een ding", Antwoord = "nee"},
                            },
                            lat = 55.220214f,
                            lng = 6.402223f
                        }
                       },
                       tijd = 60 * 60 * 5
                   }
               );

               context.SaveChanges();

            }

        }
    }
}
