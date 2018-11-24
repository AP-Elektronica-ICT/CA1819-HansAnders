import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs/Observable";


@Injectable()
export class VragenService {
    constructor(private _http: HttpClient) { }


    postVraag(Vraag: Vraag, regioId: number, locatieId: number): Observable<Vraag[]> {
        return this._http.post<Vraag[]>("http://localhost:39858/api/Regio/"+regioId+"/"+locatieId+"/addpuzzel", Vraag)
    }

    getRegios(): Observable<Regio[]>
    {
        return this._http.get<Regio[]>("http://localhost:39858/api/Regio")
    }
    postLocatie(regioId: number, locatie: Locatie): Observable<Locatie> {
        return this._http.post<Locatie>("http://localhost:39858/api/Regio/"+regioId+"/addLocatie", locatie)
    }
}


export interface Vraag{
    Id: number,
    Vraag: string,
    Antwoord: string
}

export interface Regio{
    id: number,
    naam: string,
    locaties: Locatie[]
    tijd: number
}

export interface Locatie{
    id: number,
    locatienaam: string,
    puzzels: Vraag[],
    lng: number,
    lat: number
}