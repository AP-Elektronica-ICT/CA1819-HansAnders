import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { VragenService, Vraag, Regio, Locatie } from '../services/vragen.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html'
})
export class HomeComponent {

  id: string
  Vraag: string
  Antwoord: string
  Regios: Regio[]

  RegioSelected:number=0
  MarkerSelected: number=0
  constructor(
    public auth: AuthService,
    private _svc: VragenService
    ) {}

  ngOnInit() {
    this.auth.handleAuthentication()
    this._svc.getRegios().subscribe(result => this.Regios = result);
  }
  title: string = 'Click to add places';
  lat: number = 51.228418;
  lng: number = 4.4159007;


  clickMap($event){
    console.log($event.coords.lat);
    console.log($event.coords.lng);

    var locatie:Locatie=({
      id:0,
      locatienaam:"f",
      puzzels: [],
      lng:$event.coords.lng,
      lat:$event.coords.lat
    })

    
    //this.Regios[this.RegioSelected].locaties.push(locatie)
    this._svc.postLocatie(this.Regios[this.RegioSelected].id, locatie).subscribe(()=>{
      console.log("marker added")
      //var lastMarker = this.Regios[this.RegioSelected].locaties.length-1;
      //this.MarkerSelected = this.Regios[this.RegioSelected-1].locaties[lastMarker].Id 

      this._svc.getRegios().subscribe((result)=>{
        this.Regios = result
        var length = this.Regios[this.RegioSelected].locaties.length-1;

        this.MarkerSelected = this.Regios[this.RegioSelected].locaties[length].id
      });
    })


  }

  markerClicked(marker){
    console.log(marker.id)
    this.MarkerSelected = marker.id
  }

  RegioSelect(id: number) {
    this.RegioSelected = id
    console.log(id)
  }

  AddQuestion() {
    var vraag: Vraag=({
      Id: 0,
      Vraag: this.Vraag,
      Antwoord: this.Antwoord
    })
    this._svc.postVraag(vraag,this.RegioSelected, this.MarkerSelected).subscribe(() => {alert("Vraag Toegevoegd")} )  
  }
}


export interface position{
  lat: number,
  lng: number
}