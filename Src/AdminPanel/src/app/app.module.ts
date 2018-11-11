import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MDBBootstrapModule } from 'angular-bootstrap-md';

import { AppComponent } from './app.component';
import { RouterModule, Routes } from '@angular/router';
import { AuthService } from './services/auth.service';
import { LoginBarComponent } from './login-bar/login-bar.component';


import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './auth.guard';
import { AgmCoreModule } from '@agm/core';
import { CreateGameComponent } from './create-game/create-game.component';

const routes: Routes = [
  { path: '', component: LoginComponent},
  { path: 'home', component: HomeComponent }
 //{ path: 'home', component: HomeComponent, canActivate:[AuthGuard]}
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    LoginBarComponent,
    CreateGameComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    MDBBootstrapModule.forRoot(),
    AgmCoreModule.forRoot({
      apiKey: 'AIzaSyD5_lt4N02VyQfNEWpt5f3DnzPwnHZ35DI'
    })
  ],
  exports: [RouterModule],
  providers: [ AuthService, AuthGuard ],
  bootstrap: [AppComponent]
})
export class AppModule { }
