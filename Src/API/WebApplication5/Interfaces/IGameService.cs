using System;
using System.Collections.Generic;
using System.Text;
using Model;

namespace Interfaces
{
    public interface IGameService
    {
        int CreateGame(int teams);
        void updateGame(int gameId, int teams, int players, int puzzelId, List<int> selectedLocaties);
        bool joinGame(string displayName, int gameId, int teamId);
        Game getGame(int gameId);


    }
}
