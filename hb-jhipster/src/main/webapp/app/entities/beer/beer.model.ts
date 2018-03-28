import { BaseEntity } from './../../shared';

export const enum BeerType {
    'LAGER',
    'PILSNER',
    'PALE_ALE',
    'INDIA_PALE_ALE',
    'PORTER',
    'STOUT',
    'OTHER'
}

export class Beer implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public type?: BeerType,
        public brewery?: BaseEntity,
    ) {
    }
}
