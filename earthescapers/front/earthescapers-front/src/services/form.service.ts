import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FormService {
  subject: BehaviorSubject<FormType | undefined> = new BehaviorSubject<FormType | undefined>(undefined);

  constructor() { }

  public getSubject(): BehaviorSubject<FormType | undefined> {
    return this.subject;
  }

  public showForm(formType: FormType): void {
    this.subject.next(formType);
  }

  public hideForm(): void {
    this.subject.next(undefined);
  }
}

export enum FormType {
  UPDATE_TRIBE = 1,
  CREATE_TRIBE,
  UPDATE_PERSON,
  CREATE_PERSON
}