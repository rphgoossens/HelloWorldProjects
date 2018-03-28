/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { HelloBeerTestModule } from '../../../test.module';
import { BeerDetailComponent } from '../../../../../../main/webapp/app/entities/beer/beer-detail.component';
import { BeerService } from '../../../../../../main/webapp/app/entities/beer/beer.service';
import { Beer } from '../../../../../../main/webapp/app/entities/beer/beer.model';

describe('Component Tests', () => {

    describe('Beer Management Detail Component', () => {
        let comp: BeerDetailComponent;
        let fixture: ComponentFixture<BeerDetailComponent>;
        let service: BeerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HelloBeerTestModule],
                declarations: [BeerDetailComponent],
                providers: [
                    BeerService
                ]
            })
            .overrideTemplate(BeerDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BeerDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BeerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Beer(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.beer).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
