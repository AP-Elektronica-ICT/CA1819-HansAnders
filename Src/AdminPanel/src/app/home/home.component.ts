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

  markers: position[] = new Array
  clickMap($event){
    console.log($event.coords.lat);
    console.log($event.coords.lng);
    this.markers.push({
      lat:$event.coords.lat,
      lng:$event.coords.lng
    });
  }
  
}
export interface position{
  lat: number,
  lng: number
}