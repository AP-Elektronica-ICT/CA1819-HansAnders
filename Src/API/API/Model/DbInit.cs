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

            context.Games.Add(
                new Game()
            );

            context.Users.Add(
                new User("jantje")
            );

            context.Regios.Add(
                new regio(
                    "antwerpen", 
                    new List<Locatie> {
                        new Locatie(
                            "kerk",
                            new List<Puzzel> {
                                new VraagPuzzel("wat is 1+1", "2"),
                                new VraagPuzzel("wat is 1+1", "2"),
                                new VraagPuzzel("wat is 1+1", "2"),
                            },
                            4.65419f,
                            4.65132f
                        ),
                        new Locatie(
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
