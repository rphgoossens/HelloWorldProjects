import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { InventoryItemComponent } from './inventory-item.component';
import { InventoryItemDetailComponent } from './inventory-item-detail.component';
import { InventoryItemPopupComponent } from './inventory-item-dialog.component';
import { InventoryItemDeletePopupComponent } from './inventory-item-delete-dialog.component';

@Injectable()
export class InventoryItemResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const inventoryItemRoute: Routes = [
    {
        path: 'inventory-item',
        component: InventoryItemComponent,
        resolve: {
            'pagingParams': InventoryItemResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.inventoryItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'inventory-item/:id',
        component: InventoryItemDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.inventoryItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inventoryItemPopupRoute: Routes = [
    {
        path: 'inventory-item-new',
        component: InventoryItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.inventoryItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inventory-item/:id/edit',
        component: InventoryItemPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.inventoryItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inventory-item/:id/delete',
        component: InventoryItemDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.inventoryItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
