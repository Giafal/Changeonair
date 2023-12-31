import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Video } from '../interfaces/video';
import { Observable, forkJoin, map, switchMap } from 'rxjs';
import { User } from '../interfaces/user';

@Injectable({
  providedIn: 'root',
})
export class HomeService {
  constructor(private http: HttpClient) {}

  private apiUrl = 'http://localhost:8080/api/videos';
  headers = new HttpHeaders();

  getVideos(): Observable<Video[]> {
    return this.http.get<Video[]>(`${this.apiUrl}/getVideos`);
  }

  getVideosByName(nome: string): Observable<Video[]> {
    return this.http.get<Video[]>(`${this.apiUrl}/getVideosByNome/${nome}`);
  }

  getVideosByUsername(username: string): Observable<Video[]> {
    return this.http.get<Video[]>(
      `${this.apiUrl}/getVideosByUsernameUtente/${username}`
    );
  }

  getUsername(userId: number): Observable<string> {
    return this.http.get<string>(
      `http://localhost:8080/api/user/getUsernameById/${userId}`
    );
  }

  getUsers(): Observable<User[]> {
    this.headers = this.headers.set(
      'Authorization',
      'Bearer ' + this.getToken()
    );
    return this.http.get<User[]>('http://localhost:8080/api/user/getUsers', {
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
}
