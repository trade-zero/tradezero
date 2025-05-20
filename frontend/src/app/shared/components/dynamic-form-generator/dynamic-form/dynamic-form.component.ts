import { ChangeDetectionStrategy, Component, computed, EventEmitter, Input, OnChanges, Output, signal, SimpleChanges } from '@angular/core';
import { FormGroup, ReactiveFormsModule } from '@angular/forms';
import { FormItemBase } from '@shared/components/dynamic-form-generator/form-factory/form-item-base';
import { FormItemControlService } from '@shared/components/dynamic-form-generator/_shared/form-item-control.service';
import { CommonModule, Location } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { DynamicFormItemComponent } from '@shared/components/dynamic-form-generator/dynamic-form-item/dynamic-form-item.component';
import { DividerModule } from 'primeng/divider';

@Component({
  selector: 'app-dynamic-form',
  standalone: true,
  templateUrl: './dynamic-form.component.html',
  styleUrl: './dynamic-form.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ButtonModule,
    DynamicFormItemComponent,
    DividerModule
  ]
})
export class DynamicFormComponent implements OnChanges {

  private _formItems = signal<FormItemBase<string>[]>([]);
  private _id: any;
  private _disabled: boolean = true;
  private _title: string | undefined;

  @Input({ required: true })
  set formItems(value: FormItemBase<string>[]) {
    this._formItems.set(value);
  }

  get formItems() {
    return this._formItems();
  }

  @Input({ required: true })
  set id(value: any) {
    this._id = value;
    this._disabled = !!value
  }

  get id() {
    return this._id;
  }

  set disabled(value: boolean) {
    this._disabled = value;
  }

  get disabled() {
    return this._disabled;
  }

  @Input() set title(value: string) {
    this._title = value;
  }

  get title() {
    return this._title!;
  }


  @Output() save = new EventEmitter<unknown>();

  form = signal<FormGroup>(new FormGroup({}));

  constructor(
    private formService: FormItemControlService,
    private location: Location
  ) { }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['formItems']) {
      this.initializeForm();
    }
    if (this.id) {
      this.disableEdit();
    }
  }

  handleSave() {
    if (this.form().valid) {
      this.save.emit(this.form().getRawValue());
    } else {
      this.markFormTouched();
    }
  }

  cancel() {
    this.location.back();
  }

  validationErrors = computed(() => {
    return this.formItems
      .filter(item => this.form().get(item.key)?.invalid)
      .map(item => item.label);
  });

  enableEdit() {
    this.disabled = false;
    this.form().enable();
  }

  disableEdit() {
    this.disabled = true;
    this.form().disable();
  }

  private initializeForm() {
    if (this.formItems.length > 0) {
      this.form.set(
        this.formService.toFormGroup(this.formItems)
      );
    }
  }

  private markFormTouched() {
    Object.values(this.form().controls).forEach(control => {
      control.markAsTouched();
    });
  }
}
