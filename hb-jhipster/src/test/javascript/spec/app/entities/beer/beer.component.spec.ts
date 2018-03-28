/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HelloBeerTestModule } from '../../../test.module';
import { BeerComponent } from '../../../../../../main/webapp/app/entities/beer/beer.component';
import { BeerService } from '../../../../../../main/webapp/app/entities/beer/beer.service';
import { Beer } from '../../../../../../main/webapp/app/entities/beer/beer.model';

describe('Component Tests', () => {

    describe('Beer Management Component', () => {
        let comp: BeerComponent;
        let fixture: ComponentFixture<BeerComponent>;
        let service: BeerService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HelloBeerTestModule],
                declarations: [BeerComponent],
                providers: [
                    BeerService
                ]
            })
            .overrideTemplate(BeerComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BeerComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BeerService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Beer(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.beers[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
