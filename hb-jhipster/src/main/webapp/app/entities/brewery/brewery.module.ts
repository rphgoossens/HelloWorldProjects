import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HelloBeerSharedModule } from '../../shared';
import {
    BreweryService,
    BreweryPopupService,
    BreweryComponent,
    BreweryDetailComponent,
    BreweryDialogComponent,
    BreweryPopupComponent,
    BreweryDeletePopupComponent,
    BreweryDeleteDialogComponent,
    breweryRoute,
    breweryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...breweryRoute,
    ...breweryPopupRoute,
];

@NgModule({
    imports: [
        HelloBeerSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        BreweryComponent,
        BreweryDetailComponent,
        BreweryDialogComponent,
        BreweryDeleteDialogComponent,
        BreweryPopupComponent,
        BreweryDeletePopupComponent,
    ],
    entryComponents: [
        BreweryComponent,
        BreweryDialogComponent,
        BreweryPopupComponent,
        BreweryDeleteDialogComponent,
        BreweryDeletePopupComponent,
    ],
    providers: [
        BreweryService,
        BreweryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HelloBeerBreweryModule {}
