using System;
using System.Collections.Generic;
using System.Text;
using Interfaces;
using Model;

namespace ServiceLayer
{
    public class GameService : IGameService
    {
        public int CreateGame(int teams)
        {
            throw new NotImplementedException();
        }

        public bool joinGame(string displayName, int gameId, int teamId)
        {
            throw new NotImplementedException();
        }

        public void updateGame(int gameId, int teams, int players, int puzzelId, List<int> selectedLocaties)
        {
            throw new NotImplementedException();
        }

        public Game getGame(int gameId)
        {

            return null;
        }
    }
}
