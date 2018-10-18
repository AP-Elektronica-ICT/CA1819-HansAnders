import { Injectable } from '@angular/core';

import * as auth0 from 'auth0-js'

@Injectable()
export class AuthService {
    auth0 = new auth0.WebAuth({
        clientID: 'F8JcJPcZc64vcvqw9CF1wkoPTw6u5NoY',
        domain: 'yorick.eu.auth0.com',
        responseType: 'token id_token',
        redirectUri: 'http://localhost:4200/callback',
        scope: 'openid'
    });

    constructor() {}

    public login(): void {
        this.auth0.authorize();
    }
}