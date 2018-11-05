using System;

namespace Model
{
    public class User
    {
        public int ID { get; set; }
        public string Name { get; set; }
        public User(string name)
        {
            this.Name = name;
        }
    }
}
