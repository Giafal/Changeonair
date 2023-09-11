import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/interfaces/user';
import { Video } from 'src/app/interfaces/video';
import { ProfileService } from 'src/app/services/profile.service';
import { VideoService } from 'src/app/services/video.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  constructor(private svc: ProfileService, private videoSvc: VideoService) {}

  userData: User = {
    name: '',
    lastname: '',
    username: '',
    email: '',
    password: '',
  };

  id: string = '';
  userId: number = 0;

  videos: Video[] = [];

  ngOnInit(): void {
    this.getUserData(this.id);
    this.getUserVideo(this.userId);
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

  getUserVideo(userId: number): void {
    userId = JSON.parse(localStorage.getItem('userId')!);
    this.videoSvc.getVideoByUtente(userId).subscribe((data) => {
      this.videos = data;
    }),
      (err: any) => {
        console.log(err);
      };
  }
}
