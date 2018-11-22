import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MDBBootstrapModule } from 'angular-bootstrap-md';

import { AppComponent } from './app.component';
import { RouterModule, Routes } from '@angular/router';
import { AuthService } from './services/auth.service';
import { LoginBarComponent } from './login-bar/login-bar.component';


import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { CreateGameComponent } from './create-game/create-game.component';
import { AuthGuard } from './auth.guard';
import { AgmCoreModule } from '@agm/core';
import { FormsModule } from '@angular/forms';
import { VragenService } from './services/vragen.service';
import { HttpClientModule } from '@angular/common/http';


const routes: Routes = [
  { path: '', component: LoginComponent},
  { path: 'home', component: HomeComponent },
  { path: 'create-game', component: CreateGameComponent}
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
    }),
    FormsModule,
    HttpClientModule
  ],
  exports: [RouterModule],
  providers: [ 
    AuthService, 
    AuthGuard,
    VragenService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
