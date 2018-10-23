import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Advertisement } from './advertisement.model';
import { AdvertisementPopupService } from './advertisement-popup.service';
import { AdvertisementService } from './advertisement.service';

@Component({
    selector: 'jhi-advertisement-dialog',
    templateUrl: './advertisement-dialog.component.html'
})
export class AdvertisementDialogComponent implements OnInit {

    advertisement: Advertisement;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private advertisementService: AdvertisementService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.advertisement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.advertisementService.update(this.advertisement));
        } else {
            this.subscribeToSaveResponse(
                this.advertisementService.create(this.advertisement));
        }
    }

    private subscribeToSaveResponse(result: Observable<Advertisement>) {
        result.subscribe((res: Advertisement) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Advertisement) {
        this.eventManager.broadcast({ name: 'advertisementListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-advertisement-popup',
    template: ''
})
export class AdvertisementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private advertisementPopupService: AdvertisementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.advertisementPopupService
                    .open(AdvertisementDialogComponent as Component, params['id']);
            } else {
                this.advertisementPopupService
                    .open(AdvertisementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
