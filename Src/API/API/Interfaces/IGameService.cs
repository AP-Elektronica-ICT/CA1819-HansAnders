using System;
using System.Collections.Generic;
using System.Text;

namespace Interfaces
{
    public interface IGameService
    {
        int CreateGame(int teams);
        void updateGame(int gameId, int teams, int players, int puzzelId, List<int> selectedLocaties);
        void joinGame(string displayName, int gameId, int team);


    }
}
