export enum ColumnPipeEnum {
  CNPJ = 'CNPJ',
  CPF = 'CPF',
  DATE = 'DATE',
  PHONE = 'PHONE',
  TIME = 'TIME',
}

export type ColumnPipeName = keyof typeof ColumnPipeEnum;
export type ColumnPipeValue = typeof ColumnPipeEnum[keyof typeof ColumnPipeEnum];
