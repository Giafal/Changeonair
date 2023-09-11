import { Component, OnInit } from '@angular/core';
import { Video } from 'src/app/interfaces/video';
import { HomeService } from 'src/app/services/home.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss'],
})
export class HomepageComponent implements OnInit {
  constructor(private svc: HomeService) {}

  videos: Video[] = [];

  nome: string = '';

  ngOnInit(): void {
    this.getVideos();
  }

  getVideos(): void {
    this.svc.getVideos().subscribe((videos) => (this.videos = videos));
  }

  searchVideos(nome: string): void {
    nome = this.nome;
    this.svc
      .getVideosByName(nome)
      .subscribe((videos) => (this.videos = videos));
  }
}
