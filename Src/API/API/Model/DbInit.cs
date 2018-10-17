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

            context.Puzzels.Add(
                new VraagPuzzel("wa is 1+1", "2")
            );

            context.SaveChanges();
        }
    }
}
