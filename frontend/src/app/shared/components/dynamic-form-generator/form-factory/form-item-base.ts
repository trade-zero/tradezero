import { MaskPatternTypes } from '@shared/enum/default-masks.enum';
import { FORM_CONTROL_TYPE } from '@shared/components/dynamic-form-generator/_shared/form-control-type.enum';

export class FormItemBase<T> {

  binary: boolean;
  className: string;
  controlType: FORM_CONTROL_TYPE;
  display: 'chip' | 'comma';
  dataKey: string | undefined;
  errorMessage: string;
  filter: boolean | undefined;
  filterBy: string | undefined;
  key: string;
  label: string;
  mask: MaskPatternTypes | undefined;
  order: number;
  options: { key: string; label: string }[] | any[] | undefined;
  optionLabel: string;
  optionValue: string;
  placeholder: string;
  required: boolean;
  readOnly: boolean;
  showClear: boolean;
  type: string;
  value: T | T[] | Date | undefined ;

  constructor(
    options: {
      binary?: boolean;
      className?: string;
      controlType?: FORM_CONTROL_TYPE;
      dataKey?: string | undefined;
      display?: 'chip' | 'comma';
      errorMessage?: string;
      filter?: boolean | undefined;
      filterBy?: string | undefined;
      key?: string;
      label?: string;
      mask?: MaskPatternTypes | undefined;
      order?: number;
      placeholder?: string;
      options?: { key: string; label: string }[] | any[] | undefined;
      optionLabel?: string;
      optionValue?: string;
      readOnly?: boolean;
      required?: boolean;
      showClear?: boolean;
      type?: string;
      value?: T | T[] | Date;
    } = {},
  ) {

    this.binary = !!options.binary;
    this.className = options.className || 'col-4';
    this.controlType = options.controlType || FORM_CONTROL_TYPE.TEXT_INPUT;
    this.dataKey = options.dataKey || undefined;
    this.display = options.display || 'chip';
    this.errorMessage = options.errorMessage || 'Campo inválido';
    this.filter = !!options.filter;
    this.filterBy = options.filterBy || '';
    this.key = options.key || '';
    this.label = options.label || '';
    this.mask = options.mask || undefined;
    this.options = options.options || [];
    this.optionLabel = options.optionLabel || '';
    this.optionValue = options.optionValue || '';
    this.order = options.order === undefined ? 1 : options.order;
    this.placeholder = options.placeholder || '';
    this.readOnly = !!options.readOnly;
    this.required = !!options.required;
    this.showClear = !!options.showClear;
    this.type = options.type || '';
    this.value = options.value || [];

  }
}
