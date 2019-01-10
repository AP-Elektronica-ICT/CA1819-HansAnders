using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Model;

namespace WebApplication5.Controllers
{
    [Produces("application/json")]
    [Route("api/Game")] 
    public class GameController : Controller
    {
        private readonly GameContext _context;

        public GameController(GameContext context)
        {
            _context = context;
        }

        // GET: api/Game
        [HttpGet]
        public IEnumerable<Game> GetGames()
        {
            return _context.Games.Include(y => y.enabledLocaties).ThenInclude(t=>t.puzzels).Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).ThenInclude(o => o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r => r.regio);
        }

        // GET: api/Game/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetGame([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var game = await _context.Games.Include(y=>y.enabledLocaties).ThenInclude(t => t.puzzels).Include(t=>t.teams).ThenInclude(p=>p.Users).Include(t => t.teams).ThenInclude(o=>o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r=>r.regio).SingleOrDefaultAsync(m => m.id == id);

            if (game == null)
            {
                return NotFound();
            }

            return Ok(game);
        }

        // PUT: api/Game/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutGame([FromRoute] int id, [FromBody] Game game)
        {
            Game test = game;
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            
            _context.Entry(game).State = EntityState.Modified;
            var dbgame = await _context.Games.Include(y => y.enabledLocaties).ThenInclude(t => t.puzzels).Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).ThenInclude(o => o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r => r.regio).SingleOrDefaultAsync(m => m.id == id);


            var dbregio = _context.Regios.SingleOrDefault(m => m.id == game.regio.id);
            if (dbregio == null) return NotFound();
            dbgame.regio = dbregio;
            _context.Games.Update(dbgame);
            _context.SaveChanges();


            dbregio = _context.Regios.Include(r => r.locaties).ThenInclude(l => l.puzzels).SingleOrDefault(m => m.id == game.regio.id);
            if (dbregio == null) return NotFound();
            List<Locatie> enabledlocaties = game.enabledLocaties;

            dbgame.enabledLocaties = enabledlocaties;
            /*foreach (Locatie locatie in enabledlocaties)
            {
                var dblocatie = dbregio.locaties.SingleOrDefault(m => m.Id == locatie.Id);
                if (dblocatie == null) return NotFound();
                if (dbgame.enabledLocaties == null)
                    dbgame.enabledLocaties = new List<Locatie>();
                dbgame.enabledLocaties.Add(dblocatie);
                await _context.SaveChangesAsync();
            }*/

            //dbgame.enabledLocaties = game.enabledLocaties;
            _context.Games.Update(dbgame);
            dbgame.enabledLocaties = enabledlocaties;
            _context.SaveChanges();

            return Ok(dbgame.enabledLocaties);
        }



        // POST: api/Game
        [HttpPost]
        public async Task<IActionResult> PostGame([FromBody] Game game)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            if(game != null)
            {
                List<Team> teams = game.teams;
                Regio regio = game.regio;
                List<Locatie> locaties = game.enabledLocaties;
                game.teams = null;
                game.regio = null;
                game.enabledLocaties = null;
                _context.Games.Add(game);
                _context.SaveChanges();

                game = await _context.Games.Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).ThenInclude(o => o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r => r.regio).SingleOrDefaultAsync(m => m.id == game.id);
                if (regio != null)
                {
                    var dbregio = _context.Regios.SingleOrDefault(m => m.id == regio.id);
                    if (dbregio == null) return NotFound();
                    game.regio = dbregio;
                    _context.SaveChanges();
                }
                if (teams != null)
                {
                    foreach (Team team in teams)
                    {
                        Team Team = new Team() { TeamName = team.TeamName };
                        _context.Teams.Add(Team);
                        _context.SaveChanges();
                        var dbTeam = _context.Teams.SingleOrDefault(m => m.Id == Team.Id);
                        game.teams.Add(dbTeam);
                        _context.SaveChanges();
                    }
                }
                if (locaties != null)
                {
                    var dbregio = _context.Regios.Include(r => r.locaties).ThenInclude(l => l.puzzels).SingleOrDefault(m => m.id == regio.id);
                    if (dbregio == null) return NotFound();
                    foreach (Locatie locatie in locaties)
                    {
                        //foreach(Locatie locatie in )
                        var dblocatie = dbregio.locaties.SingleOrDefault(m => m.id == locatie.id);
                        if (dblocatie == null) return NotFound();
                        if(game.enabledLocaties == null)
                            game.enabledLocaties = new List<Locatie>();
                        game.enabledLocaties.Add(dblocatie);
                        _context.SaveChanges();
                    }

                }
                return Created("Created game", game);
            }
            return BadRequest();
        }





        [HttpPost("join/{id}/{team}")]
        public async Task<IActionResult> JoinGame(int id,[FromBody] User user, [FromRoute] int team)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var game = await _context.Games.Include(p=>p.enabledLocaties).ThenInclude(m=>m.puzzels).Include(t => t.teams).ThenInclude(p=>p.Users).Include(l => l.regio).SingleOrDefaultAsync(m => m.id == id);

            if (game == null)
            {
                return NotFound();
            }
            //var dbteam = game.teams.SingleOrDefault(r => r.Id == team);
            //if (dbteam == null) return NotFound();
            //dbteam.Users.Add(user);
            for(int i = 0; i <= game.teams.Count; i++)
                if(i == team)
                {
                    game.teams[team-1].Users.Add(user);

                    _context.SaveChanges();
                    return Ok(user);
                }

            return NotFound();
        }

        [HttpPost("updateplayerlocatie/{gameid}/{teamid}/{userid}/{lat}/{lng}")]
        public async Task<IActionResult> UpdatePlayerLocatie([FromRoute] int gameid, [FromRoute] int teamid, [FromRoute] int userid, [FromRoute] float lat, [FromRoute] float lng)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var game = await _context.Games.Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).ThenInclude(o => o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).SingleOrDefaultAsync(m => m.id == gameid);

            if (game == null) return NotFound();
            
            for (int i = 0; i <= game.teams.Count; i++)
                if (i == teamid-1)
                {
                    for(int j = 0; j <= game.teams[i].Users.Count; j++)
                        if(game.teams[i].Users[j].Id == userid)
                        {
                            game.teams[i].Users[j].lat = lat;
                            game.teams[i].Users[j].lng = lng;
                            _context.SaveChanges();
                            return Ok(game);
                        }
                }
            return BadRequest();
        }

        [HttpDelete("deleteplayerlocatie/{gameid}/{teamid}/{userid}")]
        public async Task<IActionResult> DeletePlayerLocatie([FromRoute] int gameid, [FromRoute] int teamid, [FromRoute] int userid)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var game = await _context.Games.Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).ThenInclude(o => o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).SingleOrDefaultAsync(m => m.id == gameid);

            if (game == null) return NotFound();

            for (int i = 0; i <= game.teams.Count; i++)
                if (i == teamid - 1)
                {
                    for (int j = 0; j <= game.teams[i].Users.Count; j++)
                        if (game.teams[i].Users[j].Id == userid)
                        {
                            var user = game.teams[i].Users[j];
                            _context.User.Remove(user);
                            await _context.SaveChangesAsync();
                            return Ok(user);
                        }
                }
            return BadRequest();
        }





        [HttpPost("checkquestions/{gameid}/{teamid}/{locatieid}")]
        public async Task<IActionResult> UserCheckQuestions([FromBody] List<Puzzel> puzzels, [FromRoute] int gameid, [FromRoute] int locatieid, [FromRoute] int teamid)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            var game = await _context.Games.Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).ThenInclude(o => o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).SingleOrDefaultAsync(m => m.id == gameid);

            if (game == null) return NotFound("game not found");

            var locatie = game.regio.locaties.SingleOrDefault(r => r.id == locatieid);

            if (locatie == null) return NotFound("locatie not found");

            bool captured = false;
            for (int i = 0; i <= game.teams.Count; i++)
                if (i == teamid - 1)
                {
                    var team = game.teams[teamid];
                    if (team == null) return NotFound("team not found");
                    foreach (Puzzel puzzel in puzzels)
                    {
                        var dbpuzzel = locatie.puzzels.SingleOrDefault(r => r.id == puzzel.id);
                        if (dbpuzzel != null && dbpuzzel.Antwoord == puzzel.Antwoord)
                        {
                            team.CapturedLocaties.Add(locatie);
                            captured = true;

                        }
                    }
                }
            _context.SaveChanges();
            if (captured) return Ok("successfully captured location");
            else return Ok("failed to capture location");
        }





        // DELETE: api/Game/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteGame([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var game = await _context.Games.Include(y => y.enabledLocaties).ThenInclude(t => t.puzzels).Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).ThenInclude(o => o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r => r.regio).SingleOrDefaultAsync(m => m.id == id);
            if (game == null)
            {
                return NotFound();
            }
            game.enabledLocaties = null;
            game.regio = null;
            game.teams = null;
            _context.Games.Remove(game);
            _context.SaveChanges();

            return Ok(game);
        }

        private bool GameExists(int id)
        {
            return _context.Games.Any(e => e.id == id);
        }

        [HttpPut("{id}/updatelocation")]
        public async Task<IActionResult> UpdatePlayerLocation([FromRoute] int id)
        {
            return NotFound();
        }

        [HttpPost("{id}/capturelocation")]
        public async Task<IActionResult> CaptureLocation([FromRoute] int id)
        {
            return NotFound();
        }
    }
}