export enum FORM_CONTROL_TYPE {
  CHECKBOX = "CHECKBOX",
  DROPDOWN = "DROPDOWN",
  TEXT_INPUT = "TEXT_INPUT",
}

export type FormControlType = typeof FORM_CONTROL_TYPE[keyof typeof FORM_CONTROL_TYPE];
