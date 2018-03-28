/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HelloBeerTestModule } from '../../../test.module';
import { BreweryComponent } from '../../../../../../main/webapp/app/entities/brewery/brewery.component';
import { BreweryService } from '../../../../../../main/webapp/app/entities/brewery/brewery.service';
import { Brewery } from '../../../../../../main/webapp/app/entities/brewery/brewery.model';

describe('Component Tests', () => {

    describe('Brewery Management Component', () => {
        let comp: BreweryComponent;
        let fixture: ComponentFixture<BreweryComponent>;
        let service: BreweryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HelloBeerTestModule],
                declarations: [BreweryComponent],
                providers: [
                    BreweryService
                ]
            })
            .overrideTemplate(BreweryComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BreweryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BreweryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Brewery(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.breweries[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
