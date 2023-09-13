import { Component, OnInit } from '@angular/core';
import { forkJoin, map } from 'rxjs';
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

  // getVideos(): void {
  //   this.svc.getVideos().subscribe((videos) => (this.videos = videos));
  // }

  getVideos(): void {
    this.svc.getVideos().subscribe((videos) => {
      // Per ogni video, otteni lo username dell'utente
      console.log(videos);
      const requests = videos.map((video) =>
        this.svc.getUsername(video.utente!).pipe(
          map((username) => {
            video.username = Object.values(username)[0];
            console.log(username);
            return video;
          })
        )
      );

      // Usa forkJoin per attendere tutte le richieste
      forkJoin(requests).subscribe((videosWithUsername) => {
        this.videos = videosWithUsername;
      });
    });
  }

  // searchVideos(nome: string): void {
  //   nome = this.nome;
  //   forkJoin([
  //     this.svc.getVideosByName(nome),
  //     this.svc.getVideosByUsername(nome),
  //   ]).subscribe(([videosByName, videosByUsername]) => {
  //     this.videos = [...videosByName, ...videosByUsername];
  //   });
  // }

  searchVideos(nome: string): void {
    nome = this.nome;
    forkJoin([
      this.svc.getVideosByName(nome),
      this.svc.getVideosByUsername(nome),
    ]).subscribe(([videosByName, videosByUsername]) => {
      console.log(videosByName);
      console.log(videosByUsername);
      const requests = [...videosByName, ...videosByUsername].map((video) =>
        this.svc.getUsername(video.utente!).pipe(
          map((username) => {
            video.username = Object.values(username)[0];
            console.log(video.utente);
            return video;
          })
        )
      );

      forkJoin(requests).subscribe((videosWithUsername) => {
        this.videos = videosWithUsername;
      });
    });
  }
}
