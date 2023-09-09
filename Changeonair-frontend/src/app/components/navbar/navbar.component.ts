import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit, AfterViewInit {
  loggedIn: boolean = false;

  constructor(public authSvc: AuthService, private router: Router) {}

  ngAfterViewInit(): void {
    this.isLoggedIn();
  }

  ngOnInit() {
    this.isLoggedIn();
  }

  logout() {
    this.authSvc.logout();
    this.loggedIn = false;
  }

  isLoggedIn() {
    const userJson = localStorage.getItem('user');
    if (userJson) {
      this.loggedIn = true;
    }
  }
}
