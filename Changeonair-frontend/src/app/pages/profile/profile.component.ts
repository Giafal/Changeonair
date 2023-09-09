import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/interfaces/user';
import { ProfileService } from 'src/app/services/profile.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  constructor(private svc: ProfileService) {}

  userData: User = {
    name: '',
    lastname: '',
    username: '',
    email: '',
    password: '',
  };

  id: string = '';

  ngOnInit(): void {
    this.getUserData(this.id);
  }

  getUserData(id: string): void {
    id = localStorage.getItem('userId')!;
    this.svc.getById(id).subscribe((data) => {
      this.userData = data;
    }),
      (err: { error: { message: any } }) => {
        console.log(err.error.message);
      };
  }
}
