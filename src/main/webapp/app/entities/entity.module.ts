import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AdvertisementAppAdvertisementModule } from './advertisement/advertisement.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AdvertisementAppAdvertisementModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AdvertisementAppEntityModule {}
