import { Component, Input } from '@angular/core';
import { FormItemBase } from '@shared/components/dynamic-form-generator/form-factory/form-item-base';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { DropdownModule } from 'primeng/dropdown';

@Component({
  selector: 'app-dynamic-dropdown',
  template: `
    <p-dropdown [formControl]="formControl"
                [filter]="formItem.filter"
                [filterBy]="formItem.filterBy"
                [id]="formItem.key"
                [optionLabel]="formItem!.optionLabel!"
                [optionValue]="formItem!.optionValue!"
                [options]="formItem!.options!"
                [placeholder]="formItem!.placeholder!"
                [showClear]="formItem.showClear"
                [style]="{'width':'100%'}"
                appendTo="body"
                class="dynamicform-dropdown"/>
  `,
  styles: ``,
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    DropdownModule
  ]
})
export class DynamicDropdownComponent {
  @Input() formItem!: FormItemBase<any>;
  @Input() formControl!: FormControl;
}

