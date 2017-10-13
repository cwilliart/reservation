/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ReservationTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CreneauDetailComponent } from '../../../../../../main/webapp/app/entities/creneau/creneau-detail.component';
import { CreneauService } from '../../../../../../main/webapp/app/entities/creneau/creneau.service';
import { Creneau } from '../../../../../../main/webapp/app/entities/creneau/creneau.model';

describe('Component Tests', () => {

    describe('Creneau Management Detail Component', () => {
        let comp: CreneauDetailComponent;
        let fixture: ComponentFixture<CreneauDetailComponent>;
        let service: CreneauService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ReservationTestModule],
                declarations: [CreneauDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CreneauService,
                    JhiEventManager
                ]
            }).overrideTemplate(CreneauDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CreneauDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CreneauService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Creneau(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.creneau).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
