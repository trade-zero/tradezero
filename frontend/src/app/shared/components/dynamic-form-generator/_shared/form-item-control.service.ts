import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FormItemBase } from '@shared/components/dynamic-form-generator/form-factory/form-item-base';

@Injectable({
	providedIn : 'root'
})
export class FormItemControlService {

  toFormGroup(questions: FormItemBase<string>[]) {
    const group: any = {};
    questions.forEach((question) => {
      group[question.key] = question.required
        ? new FormControl(question.value || '', Validators.required)
        : new FormControl(question.value || '');
    });
    return new FormGroup(group);
  }
}
