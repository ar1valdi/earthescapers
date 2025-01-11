import { Component, Input, OnDestroy } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, map } from 'rxjs';
import { FormService, FormType } from 'src/services/form.service';
import { AlienTribe } from '../models/AlienTribe';
import { AlienTribeService } from 'src/services/alien-tribe-service.service';
import { PeopleService } from 'src/services/people.service';
import { Person } from '../models/Person';

@Component({
  selector: 'app-form-wrapper',
  templateUrl: './form-wrapper.component.html',
  styleUrls: ['./form-wrapper.component.scss']
})
  export class FormWrapperComponent implements OnDestroy {  
  activeFormType: FormType | undefined = undefined;
  form: FormGroup | undefined;
  formSubject: BehaviorSubject<FormType | undefined>;
  formTitle = '';
  defaultValuesMethod = (f: FormGroup) => {};

  createTribeFormFields = [
    { name: 'Tribe name', label: 'Tribe name', type: 'text', value: '', validators: [Validators.required] },
    { name: 'Population', label: 'Population', type: 'number', validators: [Validators.required, Validators.min(0)] },
    { name: 'Spaceship speed', label: 'Spaceship speed', type: 'number', validators: [Validators.required, Validators.min(0)] }
  ];

  updateTribeFormFields = [
    { name: 'Tribe name', label: 'Tribe name', type: 'text', value: 'default', validators: [Validators.required] },
    { name: 'Population', label: 'Population', type: 'number', value: 'default', validators: [Validators.required, Validators.min(0)] },
    { name: 'Spaceship speed', label: 'Spaceship speed', type: 'number', value: 'default', validators: [Validators.required, Validators.min(0)] }
  ];

  createPersonFormFields = [
    { name: 'Name', label: 'Imię', type: 'text', value: '',  validators: [Validators.required] },
    { name: 'Surname', label: 'Nazwisko', type: 'text', validators: [Validators.required] },
    { name: 'Age', label: 'Wiek', type: 'number', validators: [Validators.required, Validators.min(0)] },
    { name: 'KidnappedBy', label: 'Porwany przez (id)', type: 'text', validators: [Validators.required] },
  ];
  
  updatePersonFormFields = [
    { name: 'Name', label: 'Imię', type: 'text', value: '',  validators: [Validators.required] },
    { name: 'Surname', label: 'Nazwisko', type: 'text', validators: [Validators.required] },
    { name: 'Age', label: 'Wiek', type: 'number', validators: [Validators.required, Validators.min(0)] },
    { name: 'KidnappedBy', label: 'Porwany przez (id)', type: 'text', validators: [Validators.required] },
  ];

  chosenFormFields = this.createTribeFormFields;
  chosenSubmitMethod: () => void = () => {};

  constructor(private fb: FormBuilder, private fs: FormService, private router: Router,
    private tribeService: AlienTribeService, private peopleService: PeopleService,
    private route: ActivatedRoute
  ) { 
    this.buildForm().then((form) => {this.form = form});
    this.formSubject = this.fs.getSubject();
    this.formSubject.subscribe({
      next: (formType) => {
        if (formType) {
          this.showForm(formType);
        } else {
          this.hideForm();
        }
      }
    });
  }

  ngOnDestroy(): void {
    this.formSubject.unsubscribe();
  }

  createTribeOnSubmit(): void {
    this.markAllAsTouched();

    if (!this.form!.valid) {
      return;
    }

    const tribe: AlienTribe = {
      id: '',
      name: this.form!.get('Tribe name')!.value,
      population: this.form!.get('Population')!.value,
      spaceshipSpeed: this.form!.get('Spaceship speed')!.value
    }

    this.tribeService.add(tribe).subscribe(
      (next) => {
        this.fs.hideForm();
        this.router.navigate(['/']).then(() => {
          this.router.navigate(['/tribes']);
        }); 
      }
    );
  }

  createPersonOnSubmit(): void {
    this.markAllAsTouched();

    if (!this.form!.valid) {
      return;
    }

    const person: Person = {
      id: '',
      name: this.form!.get('Name')!.value,
      surname: this.form!.get('Surname')!.value,
      age: this.form!.get('Age')!.value,
      kidnappedBy: this.form!.get('KidnappedBy')!.value
    }

    this.peopleService.add(person).subscribe(
      (next) => {
        this.fs.hideForm();
        this.router.navigate(['/people']);
      }
    );
  }

  updateTribeOnSubmit(): void {
    this.markAllAsTouched();

    if (!this.form!.valid) {
      return;
    }

    const tribe: AlienTribe = {
      id: this.route.snapshot.queryParamMap.get('id')!,
      name: this.form!.get('Tribe name')!.value,
      population: this.form!.get('Population')!.value,
      spaceshipSpeed: this.form!.get('Spaceship speed')!.value
    }

    this.tribeService.update(tribe).subscribe(
      (next) => {
        this.fs.hideForm();
        this.router.navigate(['/tribe'], {queryParams: { id: tribe.id }});
      }
    );
  }

  updatePersonOnSubmit(): void {
    this.markAllAsTouched();

    if (!this.form!.valid) {
      return;
    }

    const person: Person = {
      id: this.route.snapshot.queryParamMap.get('personFormId')!,
      name: this.form!.get('Name')!.value,
      surname: this.form!.get('Surname')!.value,
      age: this.form!.get('Age')!.value,
      kidnappedBy: this.form!.get('KidnappedBy')!.value
    }

    this.peopleService.update(person).subscribe(
      (next) => {
        this.fs.hideForm();
        this.router.navigate(['/']).then(() => {
          this.router.navigate(['/person'], { queryParams: { id: person.id }});
        });
      }
    );

  }
  
  protected onClosedClicked() {
    this.fs.hideForm();
  }

  async buildForm(): Promise<FormGroup> {
    this.defaultValuesMethod = (f: FormGroup) => {};

    switch (this.activeFormType) {
      case FormType.CREATE_TRIBE:
        this.chosenFormFields = this.createTribeFormFields;
        this.formTitle = 'Create Tribe';
        this.chosenSubmitMethod = this.createTribeOnSubmit;
        break;
      case FormType.UPDATE_TRIBE:
        this.chosenFormFields = this.updateTribeFormFields;
        this.formTitle = 'Update Tribe';
        this.chosenSubmitMethod = this.updateTribeOnSubmit;
        this.defaultValuesMethod = this.getUpdateTribeDefaultValues;
        break;
      case FormType.CREATE_PERSON:
        this.chosenFormFields = this.createPersonFormFields;  
        this.formTitle = 'Create Person';
        this.chosenSubmitMethod = this.createPersonOnSubmit;
        this.defaultValuesMethod = this.getCreatePersonDefaultValues;
        break;
      case FormType.UPDATE_PERSON:
        this.chosenFormFields = this.updatePersonFormFields;
        this.formTitle = 'Update Person';
        this.chosenSubmitMethod = this.updatePersonOnSubmit;
        this.defaultValuesMethod = this.getUpdatePersonDefaultValues;
        break;
    }

    return this.createFormObject();
  }

  async sleep(ms: number): Promise<void> {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

  createFormObject(): FormGroup {
    const formControls = this.chosenFormFields.reduce((acc, field) => {
      acc[field.name] = [field.value, field.validators];
      return acc;
    }, {} as any);

    return this.fb.group(formControls);
  }

  getUpdatePersonDefaultValues(form: FormGroup) {
    this.route.queryParams.subscribe({
      next: (params) => {
        const id = params['personFormId'];
        if (!id) {
          return;
        }

        this.peopleService.getById(id).subscribe({
          next: (person: Person) => {
            form.setValue({
              Name: person.name,
              Surname: person.surname,
              Age: person.age,
              KidnappedBy: person.kidnappedBy
            });
          }
        });
        
        form.get('KidnappedBy')?.disable();
      }
    });
  }

  getUpdateTribeDefaultValues(form: FormGroup) {
    this.route.queryParams.subscribe({
      next: (params) => {
        const id = params['id'];
        if (!id) {
          return;
        }

        this.tribeService.getById(id).subscribe({
          next: (tribe: AlienTribe) => {
            form.setValue({
              'Tribe name': tribe.name,
              'Population': tribe.population,
              'Spaceship speed': tribe.spaceshipSpeed
            });
          }
        });
      }
    });
  }

  getCreatePersonDefaultValues(form: FormGroup) {
    this.route.queryParams.subscribe({
      next: (params) => {
        const id = params['tribeFormId'];
        if (!id) {
          return;
        }

        form.setValue({
          Name: '',
          Surname: '',
          Age: '',
          KidnappedBy: id
        });
        form.get('KidnappedBy')?.disable();
      }
    });
  }

  private showForm(formType: FormType): void {
    this.activeFormType = formType;
    this.buildForm().then((form) => {
      this.form = form;
      this.defaultValuesMethod(this.form);
    });
  }

  private hideForm(): void {
    this.activeFormType = undefined;
    this.router.navigate([this.router.url.split('?')[0]] );
  }

  private markAllAsTouched(): void {
    if (!this.form) {
      return
    }

    Object.keys(this.form.controls).forEach((key) => {
      this.form!.get(key)!.markAsTouched();
    });
  }
}