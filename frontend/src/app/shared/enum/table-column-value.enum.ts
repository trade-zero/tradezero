export enum ColumnTypeEnum {
  DEFAULT = 'DEFAULT',
  NESTED = 'NESTED',
  DEFAULT_BUTTONS = 'DEFAULT_BUTTONS',
  CUSTOM_TEMPLATE = 'CUSTOM_TEMPLATE',
}

export type ColumnTypeName = keyof typeof ColumnTypeEnum;
export type ColumnTypeValue = typeof ColumnTypeEnum[keyof typeof ColumnTypeEnum];
