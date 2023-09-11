import { Component, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { UploadService } from 'src/app/services/upload.service';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.scss'],
})
export class UploadComponent {
  @ViewChild('f') form!: NgForm;

  video = {
    nome: '',
    descrizione: '',
    organizzazione: '',
  };
  selectedFile: File | null = null;

  constructor(private svc: UploadService, private router: Router) {}

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }

  onSubmit() {
    if (this.selectedFile) {
      const formData = new FormData();
      formData.append('file', this.selectedFile);
      formData.append('nome', this.video.nome);
      formData.append('descrizione', this.video.descrizione);
      formData.append('organizzazione', this.video.organizzazione);

      console.log(this.selectedFile);
      console.log(this.video);
      console.log(formData.get('file'));
      console.log(formData.get('nome'));
      console.log(formData.get('descrizione'));
      console.log(formData.get('organizzazione'));

      this.svc.creaVideo(formData).subscribe(
        (response) => {
          console.log('File caricato con successo:', response);
          alert('File caricato con successo');
          this.router.navigate(['']);
        },
        (error) => {
          console.error('Errore durante il caricamento del file:', error);
        }
      );
    }
  }
}
