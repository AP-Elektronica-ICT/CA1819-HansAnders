using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.AspNetCore.SignalR;
using Microsoft.EntityFrameworkCore;
using Model;
using Newtonsoft.Json;

namespace WebApplication5.Hubs
{
    public class GameSessionHub : Hub
    {
        private readonly GameContext _context;

        public GameSessionHub(GameContext context)
        {
            _context = context;
        }
        public async Task SendMessage(string user, string message)
        {
            await Clients.All.SendAsync("ReceiveMessage", user, message);
        }

        public Task JoinRoom(string lobbyId)
        {
            return Groups.AddToGroupAsync(Context.ConnectionId, lobbyId);
            pushGameData(lobbyId);
        }

        public Task LeaveRoom(string lobbyId)
        {
            return Groups.RemoveFromGroupAsync(Context.ConnectionId, lobbyId);
        }

        public void pushGameData(string lobbyId)
        {
            Game game = _context.Games.Include(y => y.enabledLocaties).ThenInclude(t => t.puzzels).Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).ThenInclude(o => o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r => r.regio).SingleOrDefault(m => m.ID == Convert.ToInt32(lobbyId));

            if (game != null)
            {
                var json = JsonConvert.SerializeObject(game);
                Clients.Group(lobbyId).SendAsync(json);
            }
        }


        public async Task SendCaptureInfo(string lobbyId, string team)
        {
            //await Clients.All.SendAsync("ReceiveMessage", user, message);
        }

    }
}
