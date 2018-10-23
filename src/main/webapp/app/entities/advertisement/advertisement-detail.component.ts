import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Advertisement } from './advertisement.model';
import { AdvertisementService } from './advertisement.service';

@Component({
    selector: 'jhi-advertisement-detail',
    templateUrl: './advertisement-detail.component.html'
})
export class AdvertisementDetailComponent implements OnInit, OnDestroy {

    advertisement: Advertisement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private advertisementService: AdvertisementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAdvertisements();
    }

    load(id) {
        this.advertisementService.find(id).subscribe((advertisement) => {
            this.advertisement = advertisement;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAdvertisements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'advertisementListModification',
            (response) => this.load(this.advertisement.id)
        );
    }
}
