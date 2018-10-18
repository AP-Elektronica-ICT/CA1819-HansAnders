using System;
using System.Collections.Generic;
using System.Text;

namespace Model
{
    class Team
    {
        List<User> Users;
        string TeamName;
        public Team(string teamname, List<User> users)
        {
            this.TeamName = teamname;
            this.Users = users;
        }
    }
}
