import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Video } from 'src/app/interfaces/video';
import { HomeService } from 'src/app/services/home.service';

@Component({
  selector: 'app-userdetail',
  templateUrl: './userdetail.component.html',
  styleUrls: ['./userdetail.component.scss'],
})
export class UserdetailComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private homeService: HomeService
  ) {}

  username!: string;

  videos: Video[] = [];

  ngOnInit(): void {
    this.route.paramMap.subscribe((params: ParamMap) => {
      this.username = params.get('username')!;
      this.loadVideos();
    });
  }

  loadVideos() {
    this.homeService.getVideosByUsername(this.username).subscribe(
      (video) => {
        this.videos = video;
      },
      (err) => {
        console.log(err);
      }
    );
  }
}
