using System;
using System.Collections.Generic;
using System.Text;
using Interfaces;
using Model;
using RepositoryLayer;

namespace ServiceLayer
{
    public class RegioService : IRegioService
    {
        private RegioRepository _regioRepository;

        public RegioService(GameContext gameContext) {
            _regioRepository = new RegioRepository(gameContext);
        }
        public bool addGame(Regio regio)
        {
            if (regio != null)
            {
                _regioRepository.addGame(regio);
                return true;
            }
            else {
                return false;
            }
            
        }
    }
}
