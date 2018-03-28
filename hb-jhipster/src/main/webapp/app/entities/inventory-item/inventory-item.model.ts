import { BaseEntity } from './../../shared';

export const enum ServingType {
    'CAN',
    'BOTTLE'
}

export class InventoryItem implements BaseEntity {
    constructor(
        public id?: number,
        public itemDescription?: string,
        public serving?: ServingType,
        public amount?: number,
        public rating?: number,
        public beer?: BaseEntity,
    ) {
    }
}
