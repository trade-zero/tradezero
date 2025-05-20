import { FormItemBase } from './form-item-base';
import { FORM_CONTROL_TYPE } from '@shared/components/dynamic-form-generator/_shared/form-control-type.enum';

export class FormItemInputText extends FormItemBase<string> {

  override controlType = FORM_CONTROL_TYPE.TEXT_INPUT;

}
