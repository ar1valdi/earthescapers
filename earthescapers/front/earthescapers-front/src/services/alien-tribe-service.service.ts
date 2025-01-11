import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { AlienTribe } from 'src/app/models/AlienTribe';

@Injectable({
  providedIn: 'root'
})
export class AlienTribeService {
  private defualtUrl = 'http://localhost:8080/api/tribes';

  constructor(private http: HttpClient) {}

  getAll(): Observable<AlienTribe[]> {
    return this.http.get< {alienTribes: AlienTribe[]}>(this.defualtUrl).pipe(
      map(response => response.alienTribes)
    );
  }

  remove(id: string): void {
    this.http.delete(`${this.defualtUrl}/${id}`).subscribe();
  }

  add(tribe: AlienTribe): Observable<AlienTribe> {
    return this.http.post<AlienTribe>(this.defualtUrl, {
      name: tribe.name,
      population: tribe.population,
      spaceshipSpeed: tribe.spaceshipSpeed
    });
  }

  update(tribe: AlienTribe): Observable<AlienTribe> {
    return this.http.put<AlienTribe>(`${this.defualtUrl}/${tribe.id}`, {
      id: tribe.id,
      name: tribe.name,
      population: tribe.population,
      spaceshipSpeed: tribe.spaceshipSpeed
    });
  }

  getById(id: string): Observable<AlienTribe> {
    return this.http.get<AlienTribe>(`${this.defualtUrl}/${id}`);
  }
}
