import { BaseEntity } from './../../shared';

export class Brewery implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public countryName?: string,
    ) {
    }
}
