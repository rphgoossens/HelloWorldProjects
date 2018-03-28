/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { HelloBeerTestModule } from '../../../test.module';
import { ItemStockLevelDetailComponent } from '../../../../../../main/webapp/app/entities/item-stock-level/item-stock-level-detail.component';
import { ItemStockLevelService } from '../../../../../../main/webapp/app/entities/item-stock-level/item-stock-level.service';
import { ItemStockLevel } from '../../../../../../main/webapp/app/entities/item-stock-level/item-stock-level.model';

describe('Component Tests', () => {

    describe('ItemStockLevel Management Detail Component', () => {
        let comp: ItemStockLevelDetailComponent;
        let fixture: ComponentFixture<ItemStockLevelDetailComponent>;
        let service: ItemStockLevelService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HelloBeerTestModule],
                declarations: [ItemStockLevelDetailComponent],
                providers: [
                    ItemStockLevelService
                ]
            })
            .overrideTemplate(ItemStockLevelDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ItemStockLevelDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemStockLevelService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ItemStockLevel(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.itemStockLevel).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
