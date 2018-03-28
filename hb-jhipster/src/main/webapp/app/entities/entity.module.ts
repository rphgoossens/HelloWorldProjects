import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HelloBeerBeerModule } from './beer/beer.module';
import { HelloBeerBreweryModule } from './brewery/brewery.module';
import { HelloBeerInventoryItemModule } from './inventory-item/inventory-item.module';
import { HelloBeerItemStockLevelModule } from './item-stock-level/item-stock-level.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        HelloBeerBeerModule,
        HelloBeerBreweryModule,
        HelloBeerInventoryItemModule,
        HelloBeerItemStockLevelModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HelloBeerEntityModule {}
