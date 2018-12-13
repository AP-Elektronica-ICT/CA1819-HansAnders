import { Component, OnInit } from '@angular/core';
import { CreateGameService, Regio } from '../services/create-game.service';

@Component({
  selector: 'app-create-game',
  templateUrl: './create-game.component.html',
  styleUrls: ['./create-game.component.scss']
})
export class CreateGameComponent implements OnInit {

  naam: string;
  plaats: string;
  tijd: number;
  regios: Regio[];
  constructor(private _service: CreateGameService) {
   }

  ngOnInit() {
    this._service.getRegios().subscribe(result => this.regios = result);
  }

  postRegio(name, city, timespan){
    var regio: Regio={
      naam: name,
      tijd: +timespan,
      plaats: city
    }
    console.log(regio);
    this._service.postRegio(regio).subscribe(()=>{
      this._service.getRegios().subscribe(result => this.regios = result);
    });
  }
}