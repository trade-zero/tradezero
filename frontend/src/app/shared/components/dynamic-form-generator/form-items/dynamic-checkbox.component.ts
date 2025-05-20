import { Component, Input } from '@angular/core';
import { FormItemBase } from '@shared/components/dynamic-form-generator/form-factory/form-item-base';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CheckboxModule } from 'primeng/checkbox';

@Component({
  selector: 'app-dynamic-checkbox',
  template: `
    <p-checkbox [binary]="formItem.binary"
                [formControl]="formControl"
                [id]="formItem.key"
                [inputId]="formItem.key"
                [value]="formItem.key">
    </p-checkbox>
  `,
  styles: ``,
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    CheckboxModule
  ]
})
export class DynamicCheckboxComponent {
  @Input() formItem!: FormItemBase<any>;
  @Input() formControl!: FormControl;
}
