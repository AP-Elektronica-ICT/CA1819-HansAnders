import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs/Observable";


@Injectable()
export class VragenService {
    constructor(private _http: HttpClient) { }

    getVraag() : Observable<Vraag>
    {
        return this._http.get<Vraag>(`http://api.openweathermap.org/data/2.5/forecast/daily?q=Antwerpen&units=metric&cnt=4&appid=bd5e378503939ddaee76f12ad7a97608`)
    }

    postVraag(Vraag: Vraag): Observable<Vraag> {
        return this._http.post<Vraag>("http://localhost:5000/", Vraag)
    }

}


export interface Vraag{
    Id: number,
    Vraag: string,
    Antwoord: string
}