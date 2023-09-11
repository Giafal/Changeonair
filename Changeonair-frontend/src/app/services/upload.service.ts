import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UploadService {
  constructor(private http: HttpClient) {}

  apiUrl: string = 'http://localhost:8080/api/videos';
  headers = new HttpHeaders();

  creaVideo(videoData: FormData): Observable<any> {
    this.headers = this.headers.set(
      'Authorization',
      'Bearer ' + this.getToken()
    );
    return this.http.post(`${this.apiUrl}/uploadFile`, videoData, {
      headers: this.headers,
      // params: new HttpParams()
      //   .set('file', videoData.file)
      //   .set('nome', videoData.nome)
      //   .set('descrizione', videoData.descrizione)
      //   .set('organizzazione', videoData.organizzazione),
    });
  }

  getToken(): string {
    const user = localStorage.getItem('user');
    if (user) {
      const userData = JSON.parse(user);
      return userData.accessToken;
    } else {
      return '';
    }
  }
}
