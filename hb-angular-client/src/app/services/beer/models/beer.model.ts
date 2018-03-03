
export enum Type {
  LAGER = 'LAGER',
  PILSNER = 'PILSNER',
  PALE_ALE = 'PALE_ALE',
  INDIA_PALE_ALE = 'INDIA_PALE_ALE',
  STOUT = 'STOUT',
  OTHER = 'OTHER'
}

export interface Beer {
  brewery: string;
  id: number;
  name: string;
  type: Type;
}
