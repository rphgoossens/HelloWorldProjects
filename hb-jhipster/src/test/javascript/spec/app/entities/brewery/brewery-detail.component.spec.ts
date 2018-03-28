/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { HelloBeerTestModule } from '../../../test.module';
import { BreweryDetailComponent } from '../../../../../../main/webapp/app/entities/brewery/brewery-detail.component';
import { BreweryService } from '../../../../../../main/webapp/app/entities/brewery/brewery.service';
import { Brewery } from '../../../../../../main/webapp/app/entities/brewery/brewery.model';

describe('Component Tests', () => {

    describe('Brewery Management Detail Component', () => {
        let comp: BreweryDetailComponent;
        let fixture: ComponentFixture<BreweryDetailComponent>;
        let service: BreweryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HelloBeerTestModule],
                declarations: [BreweryDetailComponent],
                providers: [
                    BreweryService
                ]
            })
            .overrideTemplate(BreweryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BreweryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BreweryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Brewery(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.brewery).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
