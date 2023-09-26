import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Commento } from 'src/app/interfaces/commento';
import { User } from 'src/app/interfaces/user';
import { Video } from 'src/app/interfaces/video';
import { AuthService } from 'src/app/services/auth.service';
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
    private homeService: HomeService,
    private authService: AuthService
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

      if (data.utente) {
        this.homeService.getUsername(data.utente).subscribe(
          (username) => {
            this.video.username = Object.values(username)[0];
          },
          (error) => {
            console.error('Errore nel recupero dello username', error);
          }
        );
      }
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
    if (this.authService.loggedIn) {
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

      this.form.reset();
    }
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
        this.updateVisualizzazioni();
      },
      (err: any) => {
        console.log(err);
        this.updateVisualizzazioni();
      }
    );
  }

  addLike() {
    if (this.authService.loggedIn) {
      const formData = new FormData();
      formData.append('videoId', this.videoId);
      formData.append('userId', localStorage.getItem('userId')!);

      this.videoService.addLike(formData).subscribe(
        (res) => {
          console.log('Like aggiunto', res);
          this.updateLikeCount();
        },
        (err) => {
          console.log('Error', err);
          this.updateLikeCount();
        }
      );
    }
  }

  private updateLikeCount() {
    this.videoService.getLikeCount(this.videoId).subscribe(
      (likeCount) => {
        this.video.likeCount = likeCount;
      },
      (err) => {
        console.log('Error fetching like count', err);
      }
    );
  }

  private updateVisualizzazioni() {
    this.videoService.getVisualizzazioni(this.videoId).subscribe(
      (visualizzazioni) => {
        this.video.visualizzazioni = visualizzazioni;
      },
      (err) => {
        console.log(err);
      }
    );
  }

  generateFacebookShareLink(): string {
    const videoUrl = encodeURIComponent(this.video.url!);
    return `https://www.facebook.com/sharer/sharer.php?u=${videoUrl}`;
  }

  shareOnFacebook(): void {
    const facebookLink = this.generateFacebookShareLink();
    window.open(facebookLink, '_blank');
  }

  generateWhatsAppShareLink(): string {
    const videoUrl = encodeURIComponent(this.video.url!);
    return `whatsapp://send?text=${videoUrl}`;
  }

  shareOnWhatsapp(): void {
    const whatsappLink = this.generateWhatsAppShareLink();
    window.open(whatsappLink, '_blank');
  }

  generateTwitterShareLink(): string {
    const videoUrl = encodeURIComponent(this.video.url!);
    return `https://twitter.com/intent/tweet?url=${videoUrl}`;
  }

  shareOnTwitter(): void {
    const twitterLink = this.generateTwitterShareLink();
    window.open(twitterLink, '_blank');
  }

  generateLinkedInShareLink(): string {
    const videoUrl = encodeURIComponent(this.video.url!);
    return `https://www.linkedin.com/shareArticle?url=${videoUrl}`;
  }

  shareOnLinkedIn(): void {
    const linkedInLink = this.generateLinkedInShareLink();
    window.open(linkedInLink, '_blank');
  }
}
