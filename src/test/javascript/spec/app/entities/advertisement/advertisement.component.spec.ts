/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { AdvertisementAppTestModule } from '../../../test.module';
import { AdvertisementComponent } from '../../../../../../main/webapp/app/entities/advertisement/advertisement.component';
import { AdvertisementService } from '../../../../../../main/webapp/app/entities/advertisement/advertisement.service';
import { Advertisement } from '../../../../../../main/webapp/app/entities/advertisement/advertisement.model';

describe('Component Tests', () => {

    describe('Advertisement Management Component', () => {
        let comp: AdvertisementComponent;
        let fixture: ComponentFixture<AdvertisementComponent>;
        let service: AdvertisementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertisementAppTestModule],
                declarations: [AdvertisementComponent],
                providers: [
                    AdvertisementService
                ]
            })
            .overrideTemplate(AdvertisementComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AdvertisementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AdvertisementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Advertisement(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.advertisements[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
