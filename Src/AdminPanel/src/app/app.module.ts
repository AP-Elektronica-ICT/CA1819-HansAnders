import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { MDBBootstrapModule } from 'angular-bootstrap-md';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';

import { AuthService } from './services/auth.service';
import { LoginBarComponent } from './login-bar/login-bar.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LoginBarComponent
  ],
  imports: [
    BrowserModule,
    MDBBootstrapModule.forRoot()
  ],
  providers: [ AuthService ],
  bootstrap: [AppComponent]
})
export class AppModule { }
