import { Component, Input } from '@angular/core';
import { FormItemBase } from '@shared/components/dynamic-form-generator/form-factory/form-item-base';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { InputTextModule } from 'primeng/inputtext';

@Component({
  selector: 'app-dynamic-input',
  template: `
    <input [formControl]="formControl"
           [id]="formItem.key"
           [placeholder]="formItem.placeholder"
           [readonly]="formItem.readOnly"
           [type]="formItem.type"
           autocomplete="off"
           class="w-full"
           pInputText/>
  `,
  styles: ``,
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    InputTextModule
  ]
})
export class DynamicInputComponent {

  @Input() formItem!: FormItemBase<any>;
  @Input() formControl!: FormControl;

}
