import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ItemStockLevelComponent } from './item-stock-level.component';
import { ItemStockLevelDetailComponent } from './item-stock-level-detail.component';
import { ItemStockLevelPopupComponent } from './item-stock-level-dialog.component';
import { ItemStockLevelDeletePopupComponent } from './item-stock-level-delete-dialog.component';

export const itemStockLevelRoute: Routes = [
    {
        path: 'item-stock-level',
        component: ItemStockLevelComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.itemStockLevel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'item-stock-level/:id',
        component: ItemStockLevelDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.itemStockLevel.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const itemStockLevelPopupRoute: Routes = [
    {
        path: 'item-stock-level-new',
        component: ItemStockLevelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.itemStockLevel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'item-stock-level/:id/edit',
        component: ItemStockLevelPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.itemStockLevel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'item-stock-level/:id/delete',
        component: ItemStockLevelDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.itemStockLevel.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
