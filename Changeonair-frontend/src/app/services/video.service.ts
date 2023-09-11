import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Video } from '../interfaces/video';

@Injectable({
  providedIn: 'root',
})
export class VideoService {
  constructor(private http: HttpClient) {}

  apiUrl: string = 'http://localhost:8080/api/videos';
  headers = new HttpHeaders();

  getVideoById(videoId: number): Observable<Video> {
    this.headers = this.headers.set(
      'Authorization',
      'Bearer ' + this.getToken()
    );
    return this.http.get<Video>(`${this.apiUrl}/getVideo/${videoId}`, {
      headers: this.headers,
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

  getVideoByUtente(userId: number): Observable<Video[]> {
    this.headers = this.headers.set(
      'Authorization',
      'Bearer ' + this.getToken()
    );
    return this.http.get<Video[]>(
      `${this.apiUrl}/getVideosByUtente/${userId}`,
      {
        headers: this.headers,
      }
    );
  }
}
