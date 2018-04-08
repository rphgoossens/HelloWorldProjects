import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import {AutoCompleteModule} from 'primeng/autocomplete';

import { HelloBeerSharedModule } from '../../shared';
import {
    InventoryItemService,
    InventoryItemPopupService,
    InventoryItemComponent,
    InventoryItemDetailComponent,
    InventoryItemDialogComponent,
    InventoryItemPopupComponent,
    InventoryItemDeletePopupComponent,
    InventoryItemDeleteDialogComponent,
    inventoryItemRoute,
    inventoryItemPopupRoute,
    InventoryItemResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...inventoryItemRoute,
    ...inventoryItemPopupRoute,
];

@NgModule({
    imports: [
        AutoCompleteModule,
        HelloBeerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        InventoryItemComponent,
        InventoryItemDetailComponent,
        InventoryItemDialogComponent,
        InventoryItemDeleteDialogComponent,
        InventoryItemPopupComponent,
        InventoryItemDeletePopupComponent,
    ],
    entryComponents: [
        InventoryItemComponent,
        InventoryItemDialogComponent,
        InventoryItemPopupComponent,
        InventoryItemDeleteDialogComponent,
        InventoryItemDeletePopupComponent,
    ],
    providers: [
        InventoryItemService,
        InventoryItemPopupService,
        InventoryItemResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HelloBeerInventoryItemModule {}
