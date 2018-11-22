import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs/Observable";


@Injectable()
export class VragenService {
    constructor(private _http: HttpClient) { }


    postVraag(Vraag: Vraag, regioId: number, locatieId: number): Observable<Vraag[]> {
        return this._http.post<Vraag[]>("http://localhost:39858/api/Regio/"+regioId+"/"+locatieId+"/addpuzzel", Vraag)
    }

    getRegios(): Observable<Vraag>
    {
        return this._http.get<Vraag>("TODO")
    }

}


export interface Vraag{
    Id: number,
    Vraag: string,
    Antwoord: string
}