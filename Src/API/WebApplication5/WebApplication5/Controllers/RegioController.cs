using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Model;
using ServiceLayer;

namespace WebApplication5.Controllers
{
    [Produces("application/json")]
    [Route("api/Regio")]
    public class RegioController : Controller
    {
        private readonly GameContext _context;
        private RegioService _regioService;

        public RegioController(GameContext context)
        {
            _regioService = new RegioService(context);
            _context = context;
        }

        // GET: api/Regio
        [HttpGet]
        public IEnumerable<Regio> GetRegios()
        {
            
            return _context.Regios.Include(r=>r.locaties).ThenInclude(l=>l.puzzels);
        }

        // GET: api/Regio/5
        [HttpGet("{id}")]
        public async Task<IActionResult> Getregio([FromRoute] int id)
        {
            
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var regio = await _context.Regios.Include(r => r.locaties).ThenInclude(l => l.puzzels).SingleOrDefaultAsync(m => m.Id == id);

            if (regio == null)
            {
                return NotFound();
            }

            return Ok(regio);
        }

        // PUT: api/Regio/5
        [HttpPut("{id}")]
        public async Task<IActionResult> Putregio([FromRoute] int id, [FromBody] Regio regio)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            if (id != regio.Id)
            {
                return BadRequest();
            }

            _context.Entry(regio).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!regioExists(id))
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

        // POST: api/Regio
        [HttpPost]
        [Route("addgame")]
        public async Task<IActionResult> Postregio([FromBody] Regio regio)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            //_regioService.addGame(regio);
            if (regio != null)
            {
                List<Locatie> locaties = regio.locaties;
                regio.locaties = null;
                _context.Regios.Add(regio);
                _context.SaveChanges();
                if(locaties != null)
                {
                    foreach (Locatie locatie in locaties)
                        AddMarker(locatie, regio.Id);
                }
                return Created("Created regio", regio);
            }
            else {
                return NotFound();
            }  
        }


        // POST: api/puzzel
        [HttpPost]
        [Route("{regioid}/{LocatieId}/addpuzzel")]
        public async Task<IActionResult> AddPuzzelToMarker([FromBody] Puzzel puzzel, int LocatieId)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var locatie = await _context.locaties.Include(l=>l.puzzels).SingleOrDefaultAsync(m => m.Id == LocatieId);

            if (locatie != null)
            {
                locatie.puzzels.Add(puzzel);
                await _context.SaveChangesAsync();
                return Created("puzzel", puzzel);
            }
            else
                return NotFound();
        }

        // POST: api/Regio/5/addMarker
        [HttpPost("{id}/addLocatie")]
        public async Task<IActionResult> AddMarker([FromBody] Locatie locatie,[FromRoute] int Id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }
            var regio = await _context.Regios.Include(r => r.locaties).ThenInclude(l => l.puzzels).SingleOrDefaultAsync(m => m.Id == Id);
            regio.locaties.Add(locatie);
            //await _context.SaveChangesAsync();
            _context.SaveChanges();

            return CreatedAtAction("Getregio", new { id = locatie.Id }, locatie);
        }

        // DELETE: api/Regio/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> Deleteregio([FromRoute] int id)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var regio = await _context.Regios.SingleOrDefaultAsync(m => m.Id == id);

            if (regio == null)
            {
                return NotFound();
            }

            _context.Regios.Remove(regio);
            await _context.SaveChangesAsync();

            return Ok(regio);
        }

        private bool regioExists(int id)
        {
            return _context.Regios.Any(e => e.Id == id);
        }
    }
}