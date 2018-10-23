import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdvertisementAppSharedModule } from '../../shared';
import {
    AdvertisementService,
    AdvertisementPopupService,
    AdvertisementComponent,
    AdvertisementDetailComponent,
    AdvertisementDialogComponent,
    AdvertisementPopupComponent,
    AdvertisementDeletePopupComponent,
    AdvertisementDeleteDialogComponent,
    advertisementRoute,
    advertisementPopupRoute,
} from './';

const ENTITY_STATES = [
    ...advertisementRoute,
    ...advertisementPopupRoute,
];

@NgModule({
    imports: [
        AdvertisementAppSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AdvertisementComponent,
        AdvertisementDetailComponent,
        AdvertisementDialogComponent,
        AdvertisementDeleteDialogComponent,
        AdvertisementPopupComponent,
        AdvertisementDeletePopupComponent,
    ],
    entryComponents: [
        AdvertisementComponent,
        AdvertisementDialogComponent,
        AdvertisementPopupComponent,
        AdvertisementDeleteDialogComponent,
        AdvertisementDeletePopupComponent,
    ],
    providers: [
        AdvertisementService,
        AdvertisementPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdvertisementAppAdvertisementModule {}
