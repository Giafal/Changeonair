import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from '../interfaces/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
  constructor(private http: HttpClient) {}

  apiUrl: string = 'http://localhost:8080/api/user';

  getById(id: string): Observable<User> {
    return this.http.get<User>(this.apiUrl + '/getUser/' + id);
  }
}
