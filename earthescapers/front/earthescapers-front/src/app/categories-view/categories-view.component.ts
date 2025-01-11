import { Component, Inject } from '@angular/core';
import { AlienTribe } from '../models/AlienTribe';
import { AlienTribeService } from 'src/services/alien-tribe-service.service';
import { FormService, FormType } from 'src/services/form.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-categories-view',
  templateUrl: './categories-view.component.html',
  styleUrls: ['./categories-view.component.scss']
})
export class CategoriesViewComponent {
  alienTribes: AlienTribe[] = [];

  constructor(private alienTribeService: AlienTribeService, private formService: FormService, private router: Router) {
    this.load();
  }

  load() {
    this.alienTribeService.getAll().subscribe({
      next: (data) => {
        this.alienTribes = data;
      },
      error: (error) => {
        console.error('Error fetching alien tribes:', error);
      }
    });
  }

  remove(id: string): void {
    this.alienTribeService.remove(id);
    this.alienTribes = this.alienTribes.filter(tribe => tribe.id !== id);
  }

  onAddClicked() {
    this.formService.showForm(FormType.CREATE_TRIBE);
  }

  onEditClicked(id: string) {
    this.formService.showForm(FormType.UPDATE_TRIBE);   
    this.router.navigate([this.router.url.split('?')[0]], { queryParams: { id: id } });
  }

  onDetailsClicked(id: string) {
    this.router.navigate(['/tribe'], { queryParams: { id: id } });
  }
}
