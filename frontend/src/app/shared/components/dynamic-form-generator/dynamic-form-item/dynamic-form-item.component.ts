import { ChangeDetectionStrategy, Component, computed, Input, Type } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { DynamicInputComponent } from '@shared/components/dynamic-form-generator/form-items/dynamic-input.component';
import { DynamicDropdownComponent } from '@shared/components/dynamic-form-generator/form-items/dynamic-dropdown.component';
import { DynamicCheckboxComponent } from '@shared/components/dynamic-form-generator/form-items/dynamic-checkbox.component';
import { FormItemBase } from '@shared/components/dynamic-form-generator/form-factory/form-item-base';
import { FORM_CONTROL_TYPE, FormControlType } from '@shared/components/dynamic-form-generator/_shared/form-control-type.enum';
import { CommonModule } from '@angular/common';
import { TooltipModule } from 'primeng/tooltip';

const componentMap: Record<FormControlType, Type<any>> = {
  [FORM_CONTROL_TYPE.CHECKBOX]: DynamicCheckboxComponent,
  [FORM_CONTROL_TYPE.DROPDOWN]: DynamicDropdownComponent,
  [FORM_CONTROL_TYPE.TEXT_INPUT]: DynamicInputComponent
};

@Component({
  selector: 'app-dynamic-form-item',
  templateUrl: './dynamic-form-item.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
  standalone: true,
  imports: [
    CommonModule,
    TooltipModule
  ]
})
export class DynamicFormItemComponent {

  @Input({ required: true }) formItem!: FormItemBase<string>;
  @Input({ required: true }) form!: FormGroup;

  componentType = computed(() => {
    return componentMap[this.formItem.controlType];
  });

  control = computed(() =>
    this.form.get(this.formItem.key) as FormControl
  );

  showError = computed(() =>
    this.control().invalid &&
    (this.control().dirty || this.control().touched)
  );

  showRequiredError = computed(() =>
    this.control().invalid && this.control().errors?.['required'] &&  (this.control().dirty || this.control().touched)
  )

  protected readonly FORM_CONTROL_TYPE = FORM_CONTROL_TYPE;
}
