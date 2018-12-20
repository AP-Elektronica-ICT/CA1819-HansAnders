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
            return _context.Games.Include(y => y.enabledLocaties).ThenInclude(locs => locs.locatie).ThenInclude(t => t.puzzels).Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).ThenInclude(o => o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r => r.regio);
        }

        // GET: api/Game/5
        [HttpGet("{id}")]
        public async Task<IActionResult> GetGame([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var game = await _context.Games.Include(y=>y.enabledLocaties).ThenInclude(locs=>locs.locatie).ThenInclude(t => t.puzzels).Include(t=>t.teams).ThenInclude(p=>p.Users).Include(t => t.teams).ThenInclude(o=>o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r=>r.regio).SingleOrDefaultAsync(m => m.GameId == id);

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

            if (id != game.GameId)
            {
                return BadRequest();
            }

            //_context.Entry(game).State = EntityState.Modified;

            var dbgame = await _context.Games.Include(y => y.enabledLocaties).ThenInclude(locs => locs.locatie).ThenInclude(t => t.puzzels).Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).ThenInclude(o => o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r => r.regio).SingleOrDefaultAsync(m => m.GameId == id);

            dbgame.regioId = game.regio.Id;
            //dbgame.regio = _context.Regios.SingleOrDefault(d => d.Id == game.regio.Id);
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
            _context.Games.Add(game);
            _context.SaveChanges();
            return Created("Created game", game);
        }





        [HttpPost("join/{id}/{team}")]
        public async Task<IActionResult> JoinGame(int id,[FromBody] User user, [FromRoute] int team)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var game = await _context.Games.Include(p=>p.enabledLocaties).ThenInclude(locs => locs.locatie).ThenInclude(m=>m.puzzels).Include(t => t.teams).ThenInclude(p=>p.Users).Include(l => l.regio).SingleOrDefaultAsync(m => m.GameId == id);

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
        
        [HttpPost("{gameid}/{userid}/{vraagid}")]
        public async Task<IActionResult> UserCheckQuestion([FromBody] String antwoord, [FromRoute] int gameid, [FromRoute] int userid, [FromRoute] int vraagid)
        {
            return null;
        }





        // DELETE: api/Game/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteGame([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var game = await _context.Games.Include(y => y.enabledLocaties).ThenInclude(locs => locs.locatie).ThenInclude(t => t.puzzels).Include(t => t.teams).ThenInclude(p => p.Users).Include(t => t.teams).ThenInclude(o => o.CapturedLocaties).Include(l => l.regio).ThenInclude(m => m.locaties).ThenInclude(i => i.puzzels).Include(r => r.regio).SingleOrDefaultAsync(m => m.GameId == id);
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
            return _context.Games.Any(e => e.GameId == id);
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