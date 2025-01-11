import { Component, Input, OnInit } from '@angular/core';
import { Person } from '../models/Person';
import { PeopleService } from 'src/services/people.service';
import { FormService, FormType } from 'src/services/form.service';
import { Router } from '@angular/router';
import { AlienTribe } from '../models/AlienTribe';

@Component({
  selector: 'app-people-view',
  templateUrl: './people-view.component.html',
  styleUrls: ['./people-view.component.scss']
})
export class PeopleViewComponent implements OnInit{
  @Input() public people: Person[] | null = null;
  @Input() public parentTribe: AlienTribe | null = null;
  @Input() public showDetails = true;

  constructor(private peopleService: PeopleService, private formService: FormService, private router: Router) {}

  ngOnInit(): void {
    if (this.people === null) {
      this.load();
    }
  }

  load() {
    if (!this.parentTribe) {
      this.peopleService.getAll().subscribe({
          next: (data) => {
            this.people = data;
          },
          error: (error) => {
            console.error('Error fetching people:', error);
          }
      });
    } else {
      this.peopleService.getByTribe(this.parentTribe.id).subscribe({
        next: (data) => {
          this.people = data;
        },
        error: (error) => {
          console.error('Error fetching people:', error);
        }
      });
    }
  }

  remove(id: string): void {
    this.peopleService.remove(id);
    this.people = this.people?.filter(p => p.id !== id) || null;
  }

  onAddClicked() {
    this.formService.showForm(FormType.CREATE_PERSON);
    this.router.navigate([this.router.url.split('?')[0]], { queryParams: { tribeFormId: this.parentTribe?.id } });
  }

  onEditClicked(id: string) {
    this.formService.showForm(FormType.UPDATE_PERSON);   
    this.router.navigate([this.router.url.split('?')[0]], { queryParams: { personFormId: id } });
  }

  onDetailsClicked(id: string) {
    this.router.navigate(['/person'], { queryParams: { id: id } });
  }
}
