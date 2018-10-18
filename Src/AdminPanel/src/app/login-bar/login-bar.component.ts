import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login-bar',
  templateUrl: './login-bar.component.html',
  styleUrls: ['./login-bar.component.scss']
})

export class LoginBarComponent implements OnInit {
  authenticated: boolean = false;

  constructor(private autservice: AuthService) { 
     
  }

  ngOnInit() {
  }

  login(){
    this.autservice.login();
    this.authenticated = true;
  }
}
