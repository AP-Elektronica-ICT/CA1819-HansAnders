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
            return _context.Games.Include(t => t.Teams).ThenInclude(p => p.Users).Include(t => t.Teams).ThenInclude(o => o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r => r.regio);
        }

        // GET: api/Game/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetGame([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var game = await _context.Games.Include(t=>t.Teams).ThenInclude(p=>p.Users).Include(t => t.Teams).ThenInclude(o=>o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r=>r.regio).SingleOrDefaultAsync(m => m.ID == id);

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

            return NoContent();
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
                List<Team> teams = game.Teams;
                Regio regio = game.regio;
                List<Locatie> locaties = game.EnabledLocaties;
                game.Teams = null;
                game.regio = null;
                game.EnabledLocaties = null;
                _context.Games.Add(game);
                _context.SaveChanges();

                game = await _context.Games.Include(t => t.Teams).ThenInclude(p => p.Users).Include(t => t.Teams).ThenInclude(o => o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r => r.regio).SingleOrDefaultAsync(m => m.ID == game.ID);
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
                        _context.Teams.Add(team);
                        _context.SaveChanges();
                        var dbTeam = _context.Teams.SingleOrDefault(m => m.Id == team.Id);
                        game.Teams.Add(dbTeam);
                        _context.SaveChanges();
                    }
                }
                if (locaties != null)
                {
                    foreach (Locatie locatie in locaties)
                    {
                        var dbregio = _context.Regios.Include(r => r.locaties).ThenInclude(l => l.puzzels).SingleOrDefault(m => m.Id == regio.Id);
                        if (dbregio == null) return NotFound();
                        //foreach(Locatie locatie in )
                        var dblocatie = dbregio.locaties.SingleOrDefault(m => m.Id == locatie.Id);
                        game.EnabledLocaties = new List<Locatie>();
                        game.EnabledLocaties.Add(dblocatie);
                        _context.SaveChanges();
                    }

                }

                return Created("Created game", game);
            }
            return BadRequest();
        }




        [HttpPost("join/{id}")]
        public async Task<IActionResult> JoinGame(int id,[FromBody] User user, [FromBody] int team)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var game = await _context.Games.Include(t => t.Teams).Include(l => l.regio).ThenInclude(m => m.locaties).SingleOrDefaultAsync(m => m.ID == id);

            if (game == null)
            {
                return NotFound();
            }
            
            for(int i = 0; i < game.Teams.Count; i++)
                if(i == team)
                {
                    game.Teams[team].Users.Add(user);
                    return Ok(user);
                }

            return NotFound();
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