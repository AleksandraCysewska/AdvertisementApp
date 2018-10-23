import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AdvertisementComponent } from './advertisement.component';
import { AdvertisementDetailComponent } from './advertisement-detail.component';
import { AdvertisementPopupComponent } from './advertisement-dialog.component';
import { AdvertisementDeletePopupComponent } from './advertisement-delete-dialog.component';

export const advertisementRoute: Routes = [
    {
        path: 'advertisement',
        component: AdvertisementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Advertisements'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'advertisement/:id',
        component: AdvertisementDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Advertisements'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const advertisementPopupRoute: Routes = [
    {
        path: 'advertisement-new',
        component: AdvertisementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Advertisements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'advertisement/:id/edit',
        component: AdvertisementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Advertisements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'advertisement/:id/delete',
        component: AdvertisementDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Advertisements'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
