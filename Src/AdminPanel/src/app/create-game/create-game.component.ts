import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-create-game',
  templateUrl: './create-game.component.html',
  styleUrls: ['./create-game.component.scss']
})
export class CreateGameComponent implements OnInit {

  constructor() { }

  ngOnInit() {
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
