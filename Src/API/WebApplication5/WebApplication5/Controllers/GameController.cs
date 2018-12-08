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

            var game = await _context.Games.Include(y=>y.enabledLocaties).ThenInclude(t => t.puzzels).Include(t=>t.teams).ThenInclude(p=>p.Users).Include(t => t.teams).ThenInclude(o=>o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r=>r.regio).SingleOrDefaultAsync(m => m.ID == id);

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
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != game.ID)
            {
                return BadRequest();
            }

            _context.Entry(game).State = EntityState.Modified;
            var dbgame = await _context.Games.Include(y => y.enabledLocaties).ThenInclude(t => t.puzzels).Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).ThenInclude(o => o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r => r.regio).SingleOrDefaultAsync(m => m.ID == id);

            dbgame.regio = _context.Regios.SingleOrDefault(d => d.Id == game.regio.Id);
            dbgame.enabledLocaties = game.enabledLocaties;
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!GameExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Ok(game);
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

                game = await _context.Games.Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).ThenInclude(o => o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r => r.regio).SingleOrDefaultAsync(m => m.ID == game.ID);
                if (regio != null)
                {
                    var dbregio = _context.Regios.SingleOrDefault(m => m.Id == regio.Id);
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
                    var dbregio = _context.Regios.Include(r => r.locaties).ThenInclude(l => l.puzzels).SingleOrDefault(m => m.Id == regio.Id);
                    if (dbregio == null) return NotFound();
                    foreach (Locatie locatie in locaties)
                    {
                        //foreach(Locatie locatie in )
                        var dblocatie = dbregio.locaties.SingleOrDefault(m => m.Id == locatie.Id);
                        if (dblocatie == null) return NotFound();
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

            var game = await _context.Games.Include(p=>p.enabledLocaties).ThenInclude(m=>m.puzzels).Include(t => t.teams).ThenInclude(p=>p.Users).Include(l => l.regio).SingleOrDefaultAsync(m => m.ID == id);

            if (game == null)
            {
                return NotFound();
            }
            var dbteam = game.teams.SingleOrDefault(r => r.Id == team);
            if (dbteam == null) return NotFound();
            dbteam.Users.Add(user);
         /*   for(int i = 0; i < game.Teams.Count; i++)
                if(i == team)
                {
                    game.Teams[team].Users.Add(user);
                    return Ok(user);
                }
                */
            return Ok(game);
        }


        // DELETE: api/Game/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteGame([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var game = await _context.Games.SingleOrDefaultAsync(m => m.ID == id);
            if (game == null)
            {
                return NotFound();
            }

            _context.Games.Remove(game);
            await _context.SaveChangesAsync();

            return Ok(game);
        }

        private bool GameExists(int id)
        {
            return _context.Games.Any(e => e.ID == id);
        }
    }
}