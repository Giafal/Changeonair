import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Commento } from 'src/app/interfaces/commento';
import { User } from 'src/app/interfaces/user';
import { Video } from 'src/app/interfaces/video';
import { HomeService } from 'src/app/services/home.service';
import { VideoService } from 'src/app/services/video.service';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.scss'],
})
export class DetailComponent implements OnInit {
  constructor(
    private route: ActivatedRoute,
    private videoService: VideoService,
    private homeService: HomeService
  ) {}

  @ViewChild('f') form!: NgForm;

  videoId!: number | any;

  video: Video = {
    nome: '',
    descrizione: '',
    organizzazione: '',
  };

  visualizzazioni: number = 0;

  utenti: User[] = [];

  commenti: Commento[] = [];

  commento: Commento = {
    testo: '',
  };

  utente: User = {
    name: '',
    lastname: '',
    username: '',
    email: '',
    password: '',
  };

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.videoId = +params.get('id')!;
      this.loadVideoDetails();
      this.loadCommenti();
      this.loadUtenti();
    });
  }

  loadVideoDetails() {
    this.videoService.getVideoById(this.videoId).subscribe((data) => {
      this.video = data;
    });
  }

  loadCommenti() {
    this.videoService.getCommentiByVideoId(this.videoId).subscribe((data) => {
      this.commenti = data;
      console.log(data);
    });
  }

  loadUtenti() {
    this.homeService.getUsers().subscribe((users) => {
      this.utenti = users;
    });
  }

  creaCommento() {
    const formData = new FormData();
    formData.append('utenteId', localStorage.getItem('userId')!);
    formData.append('videoId', this.videoId);
    formData.append('testo', this.commento.testo!);

    this.videoService.creaCommento(formData).subscribe(
      (data) => {
        console.log('Commento creato con successo', data);
        this.loadCommenti();
      },
      (err) => {
        console.log('Error', err);
      }
    );
  }

  getNomeUtente(utenteId: number): string {
    // Trova l'utente corrispondente nell'array di utenti o attraverso un servizio
    const utenteCorrispondente = this.utenti.find(
      (utente) => utente.id === utenteId
    );
    return utenteCorrispondente
      ? utenteCorrispondente.username
      : 'Utente non trovato';
  }

  incrementaVisualizzazioni() {
    this.videoService.addVisualizzazione(this.videoId).subscribe(
      (res: any) => {
        console.log(res);
      },
      (err: any) => {
        console.log(err);
      }
    );
  }
}
