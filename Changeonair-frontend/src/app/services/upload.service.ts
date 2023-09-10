import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class UploadService {
  constructor(private http: HttpClient) {}

  apiUrl: string = 'http://localhost:8080/api/videos';

  creaVideo(videoData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/uploadFile`, videoData);
  }
}
