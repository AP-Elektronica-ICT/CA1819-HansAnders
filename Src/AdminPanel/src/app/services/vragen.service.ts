import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs/Observable";


@Injectable()
export class VragenService {
    constructor(private _http: HttpClient) { }


    postVraag(Vraag: Vraag, regioId: number, locatieId: number): Observable<Vraag[]> {
        return this._http.post<Vraag[]>("http://webapplication520181127093524.azurewebsites.net/api/Regio/"+regioId+"/"+locatieId+"/addpuzzel", Vraag)
    }

    getRegios(): Observable<Regio[]>
    {
        return this._http.get<Regio[]>("http://webapplication520181127093524.azurewebsites.net/api/Regio")
    }
    postLocatie(regioId: number, locatie: Locatie): Observable<Locatie> {
        return this._http.post<Locatie>("http://webapplication520181127093524.azurewebsites.net/api/Regio/"+regioId+"/addLocatie", locatie)
    }
    getVragen(markerId: number): Observable<Vraag[]>{
        return this._http.get<Vraag[]>("http://webapplication520181127093524.azurewebsites.net/")

    }
    deleteVraag(VraagId: number){
        //delete vraag
    }
}


export interface Vraag{
    Id: number,
    vraag: string,
    antwoord: string
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

export interface Game{
    id: number,
    teamamount: number,
    teams: Team[],
    EnabledLocaties: Locatie[],
    regio: Regio,
    starttijd: number
}

export interface Team{
    id: number,
    users: User[],
    capturedLocaties: Locatie[],
    teamName: number
}

export interface User{
    id: number,
    name: string,
    lng: number,
    lat: number
}