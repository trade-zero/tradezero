const countryCode = '+99'; // Reutilizável para códigos de telefone e celular

export enum MaskPatterns {
  CPF = '999.999.999-99',
  CNPJ = '99.999.999/9999-99',
  CEP = '99.999-999',
  PLACA = 'AAA-AAAA',
  HORA = 'Hh:m9',
  TELEFONE = `${countryCode} (99) 9999-9999`,
  CELULAR = `${countryCode} (99) 99999-9999`
}

export type MaskPatternTypes = keyof typeof MaskPatterns;
export type MaskPatternTypesOf = typeof MaskPatterns[keyof typeof MaskPatterns];
