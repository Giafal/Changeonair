import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Video } from '../interfaces/video';
import { Commento } from '../interfaces/commento';

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

  getCommentiByVideoId(videoId: number): Observable<Commento[]> {
    this.headers = this.headers.set(
      'Authorization',
      'Bearer ' + this.getToken()
    );
    return this.http.get<Commento[]>(
      `http://localhost:8080/api/commenti/getCommenti/${videoId}`,
      {
        headers: this.headers,
      }
    );
  }

  creaCommento(data: FormData): Observable<Commento> {
    this.headers = this.headers.set(
      'Authorization',
      'Bearer ' + this.getToken()
    );
    return this.http.post(`http://localhost:8080/api/commenti/crea`, data, {
      headers: this.headers,
    });
  }

  addVisualizzazione(id: number): Observable<any> {
    this.headers = this.headers.set(
      'Authorization',
      'Bearer ' + this.getToken()
    );
    return this.http.put<Video>(`${this.apiUrl}/addVisualizzazione/${id}`, {
      headers: this.headers,
    });
  }

  addLike(data: FormData): Observable<any> {
    this.headers = this.headers.set(
      'Authorization',
      'Bearer ' + this.getToken()
    );
    return this.http.post(`http://localhost:8080/api/likes/add`, data, {
      headers: this.headers,
    });
  }

  deleteVideo(id: number): Observable<any> {
    this.headers = this.headers.set(
      'Authorization',
      'Bearer ' + this.getToken()
    );
    return this.http.delete(`${this.apiUrl}/deleteVideo/${id}`, {
      headers: this.headers,
    });
  }

  getLikeCount(videoId: number): Observable<number> {
    const url = `http://localhost:8080/api/likes/${videoId}/likeCount`;
    return this.http.get<number>(url);
  }

  getVisualizzazioni(videoId: number): Observable<number> {
    const url = `${this.apiUrl}/${videoId}/visualizzazioni`;
    return this.http.get<number>(url);
  }
}
