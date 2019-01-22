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
            return _context.Games.Include(ggame => ggame.teams).ThenInclude(team => team.CapturedLocaties).ThenInclude(capt => capt.locatie).Include(y => y.enabledLocaties).ThenInclude(t=>t.puzzels).Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r => r.regio);
        }

        // GET: api/Game/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetGame([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var game = await _context.Games.Include(ggame => ggame.teams).ThenInclude(team => team.CapturedLocaties).ThenInclude(capt => capt.locatie).Include(y=>y.enabledLocaties).ThenInclude(t => t.puzzels).Include(t=>t.teams).ThenInclude(p=>p.Users).Include(t => t.teams).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r=>r.regio).SingleOrDefaultAsync(m => m.id == id);

            if (game == null)
            {
                return NotFound();
            }

            return Ok(game);
        }

        // PUT: api/Game/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutGame([FromRoute] int id, [FromBody] Game game) //adds regio to game and starts the game
        {
            Game test = game;
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            
            _context.Entry(game).State = EntityState.Modified;
            var dbgame = await _context.Games.Include(ggame => ggame.teams).ThenInclude(team => team.CapturedLocaties).ThenInclude(capt => capt.locatie).Include(y => y.enabledLocaties).ThenInclude(t => t.puzzels).Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r => r.regio).SingleOrDefaultAsync(m => m.id == id);


            var dbregio = _context.Regios.SingleOrDefault(m => m.id == game.regio.id);
            if (dbregio == null) return NotFound();
            dbgame.regio = dbregio;
            _context.Games.Update(dbgame);
            _context.SaveChanges();


            dbregio = _context.Regios.Include(r => r.locaties).ThenInclude(l => l.puzzels).SingleOrDefault(m => m.id == game.regio.id);
            if (dbregio == null) return NotFound();
            List<Locatie> enabledlocaties = game.enabledLocaties;

            dbgame.enabledLocaties = enabledlocaties;
            _context.Games.Update(dbgame);
            dbgame.enabledLocaties = enabledlocaties;
            _context.SaveChanges();

            return Ok(dbgame.enabledLocaties);
        }



        // POST: api/Game
        [HttpPost]
        public async Task<IActionResult> PostGame([FromBody] Game game) // creates new game
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

                game = await _context.Games.Include(ggame => ggame.teams).ThenInclude(team => team.CapturedLocaties).ThenInclude(capt => capt.locatie).Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r => r.regio).SingleOrDefaultAsync(m => m.id == game.id);
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
        public async Task<IActionResult> JoinGame(int id,[FromBody] User user, [FromRoute] int team) // user joins team in a game
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var game = await _context.Games.Include(ggame => ggame.teams).ThenInclude(tt => tt.CapturedLocaties).ThenInclude(capt => capt.locatie).Include(p=>p.enabledLocaties).ThenInclude(m=>m.puzzels).Include(t => t.teams).ThenInclude(p=>p.Users).Include(l => l.regio).SingleOrDefaultAsync(m => m.id == id);

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

        [HttpPost("updateplayerlocatie/{gameid}/{teamid}/{userid}/{lat}/{lng}")] // update player location in a game
        public async Task<IActionResult> UpdatePlayerLocatie([FromRoute] int gameid, [FromRoute] int teamid, [FromRoute] int userid, [FromRoute] float lat, [FromRoute] float lng)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var game = await _context.Games.Include(ggame => ggame.teams).ThenInclude(team => team.CapturedLocaties).ThenInclude(capt => capt.locatie).Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).SingleOrDefaultAsync(m => m.id == gameid);

            if (game == null) return NotFound();

            if (game.teams[teamid] == null) return NotFound();
            for(int j = 0; j < game.teams[teamid].Users.Count; j++)
                if(game.teams[teamid].Users[j].Id == userid)
                {
                    game.teams[teamid].Users[j].lat = lat;
                    game.teams[teamid].Users[j].lng = lng;
                    _context.SaveChanges();
                    return Ok(game);
                }
            return BadRequest();
        }

        [HttpDelete("deleteplayerlocatie/{gameid}/{teamid}/{userid}")]
        public async Task<IActionResult> DeletePlayerLocatie([FromRoute] int gameid, [FromRoute] int teamid, [FromRoute] int userid) // delete player from a game when he leaves the app
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var game = await _context.Games.Include(ggame => ggame.teams).ThenInclude(team => team.CapturedLocaties).ThenInclude(capt => capt.locatie).Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).SingleOrDefaultAsync(m => m.id == gameid);

            if (game == null) return NotFound();

            if (game.teams[teamid] == null) return NotFound();
            for (int j = 0; j < game.teams[teamid].Users.Count; j++)
                if (game.teams[teamid].Users[j].Id == userid)
                {
                    var user = game.teams[teamid].Users[j];
                    _context.User.Remove(user);
                    await _context.SaveChangesAsync();
                    return Ok(user);
                }
            return BadRequest();
        }




        


        [HttpPost("capturelocatie/{gameid}/{teamid}/{locatieid}")]
        public async Task<IActionResult> capturelocatie([FromBody] List<Puzzel> puzzels, [FromRoute] int gameid, [FromRoute] int locatieid, [FromRoute] int teamid)// player sends puzzel solutions to capture game
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            var game = await _context.Games.Include(ggame => ggame.teams).ThenInclude(someteam => someteam.CapturedLocaties).ThenInclude(capt => capt.locatie).Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(ggame=>ggame.teams).ThenInclude(someteam=> someteam.CapturedLocaties).ThenInclude(capt=>capt.locatie).SingleOrDefaultAsync(m => m.id == gameid);

            if (game == null) return NotFound("game not found");

            var locatie = game.regio.locaties.SingleOrDefault(r => r.id == locatieid);

            if (locatie == null) return NotFound("locatie not found");

            bool captured = false;
            CaptureLocatie captureLocatie = new CaptureLocatie() { locatie = locatie };
            int strength = 0;
            int captureteamid = 0;
            int captureid = 0;
            for (int i = 0; i < game.teams.Count; i++)
            {
                var someteam = game.teams[i];
                int j = 0;
                if (someteam != null && someteam.CapturedLocaties != null)
                    foreach (CaptureLocatie capLocatie in someteam.CapturedLocaties)
                    {
                        if (capLocatie.locatie.id == locatie.id)
                        {
                            strength = capLocatie.score;
                            captureteamid = i;
                            captureid = j;
                        }
                        j++;
                    }
            }
            var team = game.teams[teamid];
            if (team == null) return NotFound("team not found");
            
            foreach (Puzzel puzzel in puzzels)
            {
                var dbpuzzel = locatie.puzzels.SingleOrDefault(r => r.id == puzzel.id);
                if (dbpuzzel != null && dbpuzzel.Antwoord == puzzel.Antwoord)
                {
                    if(!captured)
                    {
                        captured = true;
                        captureLocatie.score = 0;
                    }
                    captureLocatie.score += dbpuzzel.points;
                }
            }
            if (captured && strength < captureLocatie.score )
            {
                if (team.CapturedLocaties == null) team.CapturedLocaties = new List<CaptureLocatie>();
                _context.captureLocaties.Add(captureLocatie);
                team.CapturedLocaties.Add(captureLocatie);
                if (strength > 0)
                    game.teams[captureteamid].CapturedLocaties.RemoveAt(captureid);
                _context.SaveChanges();
            }
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

            var game = await _context.Games.Include(ggame => ggame.teams).ThenInclude(team => team.CapturedLocaties).ThenInclude(capt => capt.locatie).Include(y => y.enabledLocaties).ThenInclude(t => t.puzzels).Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r => r.regio).SingleOrDefaultAsync(m => m.id == id);
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
    }
}