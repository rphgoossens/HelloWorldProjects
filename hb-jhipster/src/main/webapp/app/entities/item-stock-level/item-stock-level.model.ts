import { BaseEntity } from './../../shared';

export class ItemStockLevel implements BaseEntity {
    constructor(
        public id?: number,
        public stockDate?: any,
        public quantity?: number,
        public inventoryItem?: BaseEntity,
    ) {
    }
}
