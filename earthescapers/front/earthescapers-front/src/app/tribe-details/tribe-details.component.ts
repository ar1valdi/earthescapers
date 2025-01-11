import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AlienTribe } from '../models/AlienTribe';
import { AlienTribeService } from 'src/services/alien-tribe-service.service';
import { Person } from '../models/Person';
import { PeopleService } from 'src/services/people.service';

@Component({
  selector: 'app-tribe-details',
  templateUrl: './tribe-details.component.html',
  styleUrls: ['./tribe-details.component.scss']
})
export class TribeDetailsComponent implements OnInit{
  tribe: AlienTribe | null = null;
  kidnappedPeople: Person[] | null = null;

  constructor(
    private tribeService: AlienTribeService, 
    private route: ActivatedRoute,
    private peopleService: PeopleService  
  ){}


  ngOnInit(): void {
    const tribeId = this.route.snapshot.queryParamMap.get('id')!;
    
    this.tribeService.getById(tribeId).subscribe({
      next: (value: AlienTribe) => {
        this.tribe = value;
      }
    });

    this.peopleService.getByTribe(tribeId).subscribe({
      next: (value: Person[]) => {
        this.kidnappedPeople = value;
      }
    })
  }
}
