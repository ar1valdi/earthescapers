import { Component, OnInit } from '@angular/core';
import { Person } from '../models/Person';
import { PeopleService } from 'src/services/people.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AlienTribeService } from 'src/services/alien-tribe-service.service';
import { AlienTribe } from '../models/AlienTribe';
import { FormService, FormType } from 'src/services/form.service';

@Component({
  selector: 'app-person-details',
  templateUrl: './person-details.component.html',
  styleUrls: ['./person-details.component.scss']
})
export class PersonDetailsComponent  implements OnInit{
  person: Person | null = null;
  kidnappedByName: string | null = null;

  constructor(private peopleService: PeopleService, private alienTribeService: AlienTribeService, private route: ActivatedRoute,
    private formService: FormService, private router: Router
  ) {}

  ngOnInit(): void {
    this.peopleService.getById(this.route.snapshot.queryParamMap.get('id')!).subscribe({
      next: (value: Person) => {
        this.person = value;
        this.setKidnappedByName();
      }
    });
  }

  setKidnappedByName() {
    this.alienTribeService.getById(this.person!.kidnappedBy).subscribe({
      next: (value: AlienTribe) => {
        this.kidnappedByName = value.name;
      }
    });
  }
  
  onEditClicked() {
    if (this.person === null) {
      return;
    }
    this.formService.showForm(FormType.UPDATE_PERSON);   
    this.router.navigate([this.router.url.split('?')[0]], { queryParams: { personFormId: this.person.id } });
  }
}
