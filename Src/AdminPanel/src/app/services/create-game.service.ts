import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from "rxjs/Observable";


const url = "http://webapplication520181127093524.azurewebsites.net/api/";

@Injectable({
  providedIn: 'root'
})
export class CreateGameService {

  constructor(private _http: HttpClient) { }

  postRegio(regio: Regio): Observable<Regio> {
    return this._http.post<Regio>(url+"Regio/addgame", regio)
  }
  getRegios(): Observable<Regio[]>
  {
    return this._http.get<Regio[]>(url+"Regio")
  }
//   postRegio(data) {
//     this._http.post(url+'addgame', data)
//       .subscribe(
//         res => {
//           console.log(res);
//         },
//         err => {
//           console.log("Error occured");
//         }
// );
//  }
}
export interface Regio {
    naam: string;
    tijd: number;
    plaats: string;
}