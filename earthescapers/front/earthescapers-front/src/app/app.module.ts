import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { CategoriesViewComponent } from './categories-view/categories-view.component';
import { HttpClientModule } from '@angular/common/http';
import { HeaderComponent } from './header/header.component';
import { AppRoutingModule } from './app-routing.module';
import { FormWrapperComponent } from './form-wrapper/form-wrapper.component';
import { ReactiveFormsModule } from '@angular/forms';
import { TribeDetailsComponent } from './tribe-details/tribe-details.component';
import { PeopleViewComponent } from './people-view/people-view.component';
import { PersonDetailsComponent } from './person-details/person-details.component';

@NgModule({
  declarations: [
    AppComponent,
    CategoriesViewComponent,
    HeaderComponent,
    FormWrapperComponent,
    TribeDetailsComponent,
    PeopleViewComponent,
    PersonDetailsComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
