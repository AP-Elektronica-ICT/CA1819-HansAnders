using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using API.Model;
namespace API
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddDbContext<CityGameContext>(options => options.UseSqlServer(
               Configuration.GetConnectionString("DefaultConnection")
               )
           );
            services.AddMvc();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env, CityGameContext ctxt)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            DBInitializer.Initialize(ctxt);

            app.UseCors(builder =>
                builder.AllowAnyOrigin()
                       .AllowAnyMethod()       //for OPTIONS in pre-flight request (before DELETE)
                       .AllowAnyHeader());       // for OPTIONS in pre-flight request (before POST)    

            app.UseMvc();
        }
    }
}
