import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { BreweryComponent } from './brewery.component';
import { BreweryDetailComponent } from './brewery-detail.component';
import { BreweryPopupComponent } from './brewery-dialog.component';
import { BreweryDeletePopupComponent } from './brewery-delete-dialog.component';

export const breweryRoute: Routes = [
    {
        path: 'brewery',
        component: BreweryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.brewery.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'brewery/:id',
        component: BreweryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.brewery.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const breweryPopupRoute: Routes = [
    {
        path: 'brewery-new',
        component: BreweryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.brewery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'brewery/:id/edit',
        component: BreweryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.brewery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'brewery/:id/delete',
        component: BreweryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'helloBeerApp.brewery.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
