import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ngx-webstorage';

import { AdvertisementAppSharedModule, UserRouteAccessService } from './shared';
import { AdvertisementAppAppRoutingModule} from './app-routing.module';
import { AdvertisementAppHomeModule } from './home/home.module';
import { AdvertisementAppAdminModule } from './admin/admin.module';
import { AdvertisementAppAccountModule } from './account/account.module';
import { AdvertisementAppEntityModule } from './entities/entity.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        AdvertisementAppAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        AdvertisementAppSharedModule,
        AdvertisementAppHomeModule,
        AdvertisementAppAdminModule,
        AdvertisementAppAccountModule,
        AdvertisementAppEntityModule,
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class AdvertisementAppAppModule {}
