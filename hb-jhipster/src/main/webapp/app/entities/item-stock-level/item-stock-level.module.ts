import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HelloBeerSharedModule } from '../../shared';
import {
    ItemStockLevelService,
    ItemStockLevelPopupService,
    ItemStockLevelComponent,
    ItemStockLevelDetailComponent,
    ItemStockLevelDialogComponent,
    ItemStockLevelPopupComponent,
    ItemStockLevelDeletePopupComponent,
    ItemStockLevelDeleteDialogComponent,
    itemStockLevelRoute,
    itemStockLevelPopupRoute,
} from './';

const ENTITY_STATES = [
    ...itemStockLevelRoute,
    ...itemStockLevelPopupRoute,
];

@NgModule({
    imports: [
        HelloBeerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ItemStockLevelComponent,
        ItemStockLevelDetailComponent,
        ItemStockLevelDialogComponent,
        ItemStockLevelDeleteDialogComponent,
        ItemStockLevelPopupComponent,
        ItemStockLevelDeletePopupComponent,
    ],
    entryComponents: [
        ItemStockLevelComponent,
        ItemStockLevelDialogComponent,
        ItemStockLevelPopupComponent,
        ItemStockLevelDeleteDialogComponent,
        ItemStockLevelDeletePopupComponent,
    ],
    providers: [
        ItemStockLevelService,
        ItemStockLevelPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HelloBeerItemStockLevelModule {}
