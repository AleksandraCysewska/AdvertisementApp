/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { AdvertisementAppTestModule } from '../../../test.module';
import { AdvertisementDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/advertisement/advertisement-delete-dialog.component';
import { AdvertisementService } from '../../../../../../main/webapp/app/entities/advertisement/advertisement.service';

describe('Component Tests', () => {

    describe('Advertisement Management Delete Component', () => {
        let comp: AdvertisementDeleteDialogComponent;
        let fixture: ComponentFixture<AdvertisementDeleteDialogComponent>;
        let service: AdvertisementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertisementAppTestModule],
                declarations: [AdvertisementDeleteDialogComponent],
                providers: [
                    AdvertisementService
                ]
            })
            .overrideTemplate(AdvertisementDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdvertisementDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdvertisementService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
