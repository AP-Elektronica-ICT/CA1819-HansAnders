import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from './services/auth.service';
import { Router, RouterModule, Routes } from '@angular/router';


@Injectable()
export class AuthGuard {

  constructor( private authService : AuthService, private router : Router ) {
  }

  canActivate( route : ActivatedRouteSnapshot, state : RouterStateSnapshot ) {
    if(this.authService.isAuthenticated()) return true;
    else{this.router.navigate(['/']);}
  }
}