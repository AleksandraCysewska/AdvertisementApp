import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Advertisement } from './advertisement.model';
import { AdvertisementPopupService } from './advertisement-popup.service';
import { AdvertisementService } from './advertisement.service';

@Component({
    selector: 'jhi-advertisement-delete-dialog',
    templateUrl: './advertisement-delete-dialog.component.html'
})
export class AdvertisementDeleteDialogComponent {

    advertisement: Advertisement;

    constructor(
        private advertisementService: AdvertisementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.advertisementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'advertisementListModification',
                content: 'Deleted an advertisement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-advertisement-delete-popup',
    template: ''
})
export class AdvertisementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private advertisementPopupService: AdvertisementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.advertisementPopupService
                .open(AdvertisementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
