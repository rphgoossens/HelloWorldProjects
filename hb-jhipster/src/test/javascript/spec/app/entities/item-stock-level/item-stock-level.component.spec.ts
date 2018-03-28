/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HelloBeerTestModule } from '../../../test.module';
import { ItemStockLevelComponent } from '../../../../../../main/webapp/app/entities/item-stock-level/item-stock-level.component';
import { ItemStockLevelService } from '../../../../../../main/webapp/app/entities/item-stock-level/item-stock-level.service';
import { ItemStockLevel } from '../../../../../../main/webapp/app/entities/item-stock-level/item-stock-level.model';

describe('Component Tests', () => {

    describe('ItemStockLevel Management Component', () => {
        let comp: ItemStockLevelComponent;
        let fixture: ComponentFixture<ItemStockLevelComponent>;
        let service: ItemStockLevelService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HelloBeerTestModule],
                declarations: [ItemStockLevelComponent],
                providers: [
                    ItemStockLevelService
                ]
            })
            .overrideTemplate(ItemStockLevelComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ItemStockLevelComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemStockLevelService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ItemStockLevel(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.itemStockLevels[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
