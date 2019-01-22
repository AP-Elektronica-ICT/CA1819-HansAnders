import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs/Observable";

const url = "http://webapplication520181127093524.azurewebsites.net/api/";

@Injectable()
export class VragenService {
    constructor(private _http: HttpClient) { }


    postVraag(Vraag: Vraag, regioId: number, locatieId: number): Observable<Vraag[]> {
        return this._http.post<Vraag[]>(url+"Regio/"+regioId+"/"+locatieId+"/addpuzzel", Vraag)
    }

    getRegios(): Observable<Regio[]>
    {
        return this._http.get<Regio[]>(url+"Regio")
    }
    postLocatie(regioId: number, locatie: Locatie): Observable<Locatie> {
        return this._http.post<Locatie>(url+"Regio/"+regioId+"/addLocatie", locatie)
    }
    deleteVraag(RegioId: number, markerId: number, VraagId: number){
        return this._http.delete<void>(url+"Regio/" + RegioId + "/" + markerId + "/" + VraagId)
    }
    deleteMarker(RegioId: number, markerId: number){
        return this._http.delete<void>(url+"Regio/" + RegioId + "/" + markerId)
    }
    changeLocatienaam(RegioId: number, markerId: number, locatie: Locatie) {
        return this._http.put<Locatie>(url+"Regio/"+ RegioId + "/" + markerId + "/putlocatie", locatie)
    }
}


export interface Vraag{
    Id: number,
    vraag: string,
    antwoord: string,
    points: number
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