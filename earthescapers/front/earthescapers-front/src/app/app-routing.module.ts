import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CategoriesViewComponent } from './categories-view/categories-view.component';
import { TribeDetailsComponent } from './tribe-details/tribe-details.component';
import { PersonDetailsComponent } from './person-details/person-details.component';
import { PeopleViewComponent } from './people-view/people-view.component';

const routes: Routes = [
  { path: '', component: CategoriesViewComponent },
  { path: 'tribes', component: CategoriesViewComponent}, 
  { path: 'tribe', component: TribeDetailsComponent},
  { path: 'person', component: PersonDetailsComponent},
  { path: 'people', component: PeopleViewComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
