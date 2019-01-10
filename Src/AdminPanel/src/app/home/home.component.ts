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
  locatienaam: string
  Points: string
  Regios: Regio[]

  
  RegioSelected:number=0
  MarkerSelected: number=0
  MarkerIndexSelected: number=0
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

  markerClicked(marker, index: number){
    this.MarkerIndexSelected = index
    console.log(this.MarkerIndexSelected)
    this.MarkerSelected = marker.id
  }

  RegioSelect(id: number) {
    this.RegioSelected = id
    console.log(id + " regioid")
    this.MarkerIndexSelected = 0
  }

  AddQuestion() {
    console.log("regioId: " + this.RegioSelected + " markerid: "+ this.MarkerSelected)
    var vraag: Vraag=({
      Id: 0,
      vraag: this.Vraag,
      antwoord: this.Antwoord,
      points: parseInt(this.Points)
    })
    this._svc.postVraag(vraag,this.RegioSelected, this.MarkerSelected).subscribe(() => {
      alert("Vraag Toegevoegd")
      this._svc.getRegios().subscribe((result)=> this.Regios = result);
    })  
  }

  deleteVraag(id: number){
    console.log(this.Regios[this.RegioSelected].id)
    console.log(this.MarkerSelected)
    //console.log(this.Regios[this.RegioSelected].locaties[this.MarkerSelected].id)
    console.log(id);
    this._svc.deleteVraag(this.Regios[this.RegioSelected].id, this.MarkerSelected, id).subscribe(() => {
      alert("vraag verwijderd")
      this._svc.getRegios().subscribe((result)=> this.Regios = result);
    })
  }

  deleteMarker(){
    this._svc.deleteMarker(this.Regios[this.RegioSelected].id, this.MarkerSelected).subscribe(() => {
      alert("marker verwijderd")
      this._svc.getRegios().subscribe((result)=> this.Regios = result);
    })
  }

  changeName(){
    var locatie: Locatie=({
      id: 0,
      locatienaam: this.locatienaam,
      puzzels: null,
      lat: this.Regios[this.RegioSelected].locaties[this.MarkerIndexSelected].lat,
      lng: this.Regios[this.RegioSelected].locaties[this.MarkerIndexSelected].lng
    })

    this._svc.changeLocatienaam(this.Regios[this.RegioSelected].id, this.MarkerSelected, locatie).subscribe(() => {
      alert("Naam aangepast")
      this._svc.getRegios().subscribe((result)=> this.Regios = result);
    })
  }
}


export interface position{
  lat: number,
  lng: number
}