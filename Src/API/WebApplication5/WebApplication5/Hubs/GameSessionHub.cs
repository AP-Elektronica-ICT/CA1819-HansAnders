using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.AspNetCore.SignalR;

namespace WebApplication5.Hubs
{
    public class GameSessionHub : Hub
    {
        public void getTime(string countryZone)
        {
            TimeZone currentZone = TimeZone.CurrentTimeZone;
            DateTime currentDate = DateTime.Now;
            DateTime currentUTC = currentZone.ToUniversalTime(currentDate);
            TimeZoneInfo selectedTimeZone = TimeZoneInfo.FindSystemTimeZoneById(countryZone);
            DateTime currentDateTime = TimeZoneInfo.ConvertTimeFromUtc(currentUTC, selectedTimeZone);
            Clients.Caller.setTime(currentDateTime.ToString("h:mm:ss tt"));
        }
        public async Task SendMessage(string user, string message)
        {
            await Clients.All.SendAsync("ReceiveMessage", user, message);
        }
    }
}
