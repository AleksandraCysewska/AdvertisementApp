/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { AdvertisementAppTestModule } from '../../../test.module';
import { AdvertisementDetailComponent } from '../../../../../../main/webapp/app/entities/advertisement/advertisement-detail.component';
import { AdvertisementService } from '../../../../../../main/webapp/app/entities/advertisement/advertisement.service';
import { Advertisement } from '../../../../../../main/webapp/app/entities/advertisement/advertisement.model';

describe('Component Tests', () => {

    describe('Advertisement Management Detail Component', () => {
        let comp: AdvertisementDetailComponent;
        let fixture: ComponentFixture<AdvertisementDetailComponent>;
        let service: AdvertisementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertisementAppTestModule],
                declarations: [AdvertisementDetailComponent],
                providers: [
                    AdvertisementService
                ]
            })
            .overrideTemplate(AdvertisementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdvertisementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdvertisementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Advertisement(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.advertisement).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
