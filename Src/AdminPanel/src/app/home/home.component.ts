import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent {

  constructor(public auth: AuthService) { }
  ngOnInit() {
    this.auth.handleAuthentication()
  }
  title: string = 'Click to add places';
  lat: number = 51.228418;
  lng: number = 4.4159007;
  
}