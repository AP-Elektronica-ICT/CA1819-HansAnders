using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    public class Team
    {
        public int Id { get; set; }
        public List<User> Users { get; set; }
        public string TeamName { get; set; }
       /* public Team(string teamname, List<User> users)
        {
            this.TeamName = teamname;
            this.Users = users;
        }*/
    }
}
