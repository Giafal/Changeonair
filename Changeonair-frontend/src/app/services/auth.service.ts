import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { BehaviorSubject, map, tap } from 'rxjs';
import { ISignUp } from '../interfaces/isign-up';
import { ISignIn } from '../interfaces/isign-in';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  loggedIn: boolean = false;

  jwtHelper: JwtHelperService = new JwtHelperService();
  private authSubject = new BehaviorSubject<null | Object>(null);

  user$ = this.authSubject.asObservable();
  isLoggedIn$ = this.user$.pipe(map((data: any) => Boolean(data)));

  authLogoutTimer: any;

  constructor(private http: HttpClient, private router: Router) {
    this.restoreUser();
    this.isLoggedIn();
  }

  signin(user: ISignIn) {
    console.log(user);
    this.loggedIn = true;
    return this.http
      .post('http://localhost:8080/api/auth/login', user)
      .pipe(tap((data: any) => this.authSubject.next(data)));
  }

  signup(user: ISignUp) {
    console.log(user);
    return this.http.post('http://localhost:8080/api/auth/register', user);
  }

  restoreUser() {
    const userJson = localStorage.getItem('user');
    if (!userJson) {
      return;
    }
    const user = JSON.parse(userJson);
    if (this.jwtHelper.isTokenExpired(user.accessToken)) {
      console.log(user.accessToken);
      this.router.navigate(['/login']);
      localStorage.clear();
      return;
    } else {
      this.authSubject.next(user);
      return;
    }
  }

  logout() {
    this.loggedIn = false;
    this.authSubject.next(null);
    localStorage.removeItem('user');
    localStorage.removeItem('userId');
    localStorage.removeItem('userName');
    this.router.navigate(['']);
    if (this.authLogoutTimer) {
      clearTimeout(this.authLogoutTimer);
    }
  }

  isLoggedIn() {
    const userJson = localStorage.getItem('user');
    if (userJson) {
      this.loggedIn = true;
    }
  }
}
