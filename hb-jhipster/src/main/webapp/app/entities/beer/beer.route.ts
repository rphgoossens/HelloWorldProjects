import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { BeerComponent } from './beer.component';
import { BeerDetailComponent } from './beer-detail.component';
import { BeerPopupComponent } from './beer-dialog.component';
import { BeerDeletePopupComponent } from './beer-delete-dialog.component';

@Injectable()
export class BeerResolvePagingParams implements Resolve<any> {

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

export const beerRoute: Routes = [
    {
        path: 'beer',
        component: BeerComponent,
        resolve: {
            'pagingParams': BeerResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.beer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'beer/:id',
        component: BeerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.beer.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const beerPopupRoute: Routes = [
    {
        path: 'beer-new',
        component: BeerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.beer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'beer/:id/edit',
        component: BeerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.beer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'beer/:id/delete',
        component: BeerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.beer.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
