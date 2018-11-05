import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login-bar',
  templateUrl: './login-bar.component.html',
  styleUrls: ['./login-bar.component.scss']
})

export class LoginBarComponent {
  
  constructor(public auth: AuthService) { }

  login() {
    this.auth.login()
  }

  logout(){
    this.auth.logout()
  }

}