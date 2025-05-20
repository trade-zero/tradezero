import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DynamicFormComponent } from '@shared/components/dynamic-form-generator/dynamic-form/dynamic-form.component';
import { FormItemControlService } from '@shared/components/dynamic-form-generator/_shared/form-item-control.service';
import { ReactiveFormsModule } from '@angular/forms';
import { DynamicInputComponent } from './form-items/dynamic-input.component';
import { DynamicCheckboxComponent } from '@shared/components/dynamic-form-generator/form-items/dynamic-checkbox.component';
import { DynamicDropdownComponent } from '@shared/components/dynamic-form-generator/form-items/dynamic-dropdown.component';
import { FloatLabelModule } from 'primeng/floatlabel';
import { ButtonModule } from 'primeng/button';
import { DynamicMultiSelectComponent } from '@shared/components/dynamic-form-generator/form-items/dynamic-multi-select.component';
import { DynamicMaskInputComponent } from '@shared/components/dynamic-form-generator/form-items/dynamic-mask-input.component';
import { DynamicFormItemComponent } from '@shared/components/dynamic-form-generator/dynamic-form-item/dynamic-form-item.component';
import { TooltipModule } from 'primeng/tooltip';
import { DynamicCalendarComponent } from '@shared/components/dynamic-form-generator/form-items/dynamic-calendar.component';
import { DynamicAutocompleteComponent } from '@shared/components/dynamic-form-generator/form-items/dynamic-autocomplete.component';
import { DynamicPasswordComponent } from '@shared/components/dynamic-form-generator/form-items/dynamic-password.component';
import { DynamicFormActionsComponent } from '@shared/components/dynamic-form-generator/dynamic-form-actions/dynamic-form-actions.component';

@NgModule({
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FloatLabelModule,
    ButtonModule,
    TooltipModule,
    DynamicFormActionsComponent,
    DynamicFormItemComponent,
    DynamicFormComponent,
    DynamicInputComponent,
    DynamicCheckboxComponent,
    DynamicDropdownComponent,
    DynamicMultiSelectComponent,
    DynamicMaskInputComponent,
    DynamicCalendarComponent,
    DynamicAutocompleteComponent,
    DynamicPasswordComponent
  ],
  providers: [
    FormItemControlService
  ],
  exports: [
    DynamicFormItemComponent,
    DynamicFormComponent
  ]
})
export class DynamicFormModule {
}
