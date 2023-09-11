import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Video } from 'src/app/interfaces/video';
import { VideoService } from 'src/app/services/video.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss'],
})
export class DetailComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private videoService: VideoService
  ) {}

  videoId!: number;
  video: Video = {
    nome: '',
    descrizione: '',
    organizzazione: '',
  };

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.videoId = +params.get('id')!;
      this.loadVideoDetails();
    });
  }

  loadVideoDetails() {
    this.videoService.getVideoById(this.videoId).subscribe((data) => {
      this.video = data;
    });
  }
}
