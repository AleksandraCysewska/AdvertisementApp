import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Advertisement } from './advertisement.model';
import { AdvertisementService } from './advertisement.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-advertisement',
    templateUrl: './advertisement.component.html'
})
export class AdvertisementComponent implements OnInit, OnDestroy {
advertisements: Advertisement[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private advertisementService: AdvertisementService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.advertisementService.query().subscribe(
            (res: ResponseWrapper) => {
                this.advertisements = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAdvertisements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Advertisement) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    registerChangeInAdvertisements() {
        this.eventSubscriber = this.eventManager.subscribe('advertisementListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
