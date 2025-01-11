import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable } from 'rxjs';
import { Person } from 'src/app/models/Person';

@Injectable({
  providedIn: 'root'
})
export class PeopleService{
  private defualtUrl = 'http://localhost:8080/api/people';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Person[]> {
    return this.http.get< {people: Person[]}>(this.defualtUrl).pipe(
      map(response => response.people)
    );
  }

  getByTribe(id: string): Observable<Person[]> {
    return this.http.get< {people: Person[]}>(this.defualtUrl + '/getByTribe/' + id).pipe(
      map(response => response.people)
    );
  }

  remove(id: string): void {
    this.http.delete(`${this.defualtUrl}/${id}`).subscribe();
  }
  
  add(person: Person): Observable<Person> {
    return this.http.post<Person>(this.defualtUrl, {
      name: person.name,
      surname: person.surname,
      age: person.age,
      kidnappedBy: person.kidnappedBy
    });
  }

  update(person: Person): Observable<Person> {
    return this.http.put<Person>(`${this.defualtUrl}/${person.id}`, {
      id: person.id,
      name: person.name,
      surname: person.surname,
      age: person.age,
      kidnappedBy: person.kidnappedBy
    });
  }

  getById(id: string): Observable<Person> {
    return this.http.get<Person>(`${this.defualtUrl}/${id}`);
  }
}
