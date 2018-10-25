using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    class DbInit
    {
        public static void Initialize(GameContext context)
        {
            context.Database.EnsureCreated();

            context.Games.Add(
                new Game()
            );

            context.Users.Add(
                new User("jantje")
            );

            context.locaties.Add(
                new Locatie(
                    "antwerpen", 
                    new List<PuzzelVerzameling> {
                        new PuzzelVerzameling(
                            "kerk",
                            new List<Puzzel> {
                                new VraagPuzzel("wat is 1+1", "2"),
                                new VraagPuzzel("wat is 1+1", "2"),
                                new VraagPuzzel("wat is 1+1", "2"),
                            },
                            4.65419f,
                            4.65132f
                        ),
                        new PuzzelVerzameling(
                            "kathdraal",
                            new List<Puzzel> {
                                new VraagPuzzel("wat is 1+1", "2"),
                                new VraagPuzzel("wat is 1+1", "2"),
                                new VraagPuzzel("wat is 1+1", "2"),
                            },
                            4.789f,
                            4.123f
                        ),
                    },
                    60*60*5
                )
            );

            context.SaveChanges();
        }
    }
}
