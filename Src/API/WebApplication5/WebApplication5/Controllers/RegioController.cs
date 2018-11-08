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
    [Route("api/Regio")]
    public class RegioController : Controller
    {
        private readonly GameContext _context;

        public RegioController(GameContext context)
        {
            _context = context;
        }

        // GET: api/Regio
        [HttpGet]
        public IEnumerable<regio> GetRegios()
        {
            return _context.Regios;
        }

        // GET: api/Regio/5
        [HttpGet("{id}")]
        public async Task<IActionResult> Getregio([FromRoute] int id)
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

            return Ok(regio);
        }

        // PUT: api/Regio/5
        [HttpPut("{id}")]
        public async Task<IActionResult> Putregio([FromRoute] int id, [FromBody] regio regio)
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
        public async Task<IActionResult> Postregio([FromBody] regio regio)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            _context.Regios.Add(regio);
            await _context.SaveChangesAsync();

            return CreatedAtAction("Getregio", new { id = regio.Id }, regio);
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