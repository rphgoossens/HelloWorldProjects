import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {CalendarModule} from 'primeng/calendar';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {HelloBeerSharedModule} from '../../shared';
import {
    ItemStockLevelComponent,
    ItemStockLevelDeleteDialogComponent,
    ItemStockLevelDeletePopupComponent,
    ItemStockLevelDetailComponent,
    ItemStockLevelDialogComponent,
    ItemStockLevelPopupComponent,
    itemStockLevelPopupRoute,
    ItemStockLevelPopupService,
    itemStockLevelRoute,
    ItemStockLevelService,
} from './';

const ENTITY_STATES = [
    ...itemStockLevelRoute,
    ...itemStockLevelPopupRoute,
];

@NgModule({
    imports: [
        CalendarModule,
        BrowserAnimationsModule,
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
export class HelloBeerItemStockLevelModule {
}
