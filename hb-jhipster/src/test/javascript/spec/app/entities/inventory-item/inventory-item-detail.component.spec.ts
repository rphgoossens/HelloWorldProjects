/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { HelloBeerTestModule } from '../../../test.module';
import { InventoryItemDetailComponent } from '../../../../../../main/webapp/app/entities/inventory-item/inventory-item-detail.component';
import { InventoryItemService } from '../../../../../../main/webapp/app/entities/inventory-item/inventory-item.service';
import { InventoryItem } from '../../../../../../main/webapp/app/entities/inventory-item/inventory-item.model';

describe('Component Tests', () => {

    describe('InventoryItem Management Detail Component', () => {
        let comp: InventoryItemDetailComponent;
        let fixture: ComponentFixture<InventoryItemDetailComponent>;
        let service: InventoryItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HelloBeerTestModule],
                declarations: [InventoryItemDetailComponent],
                providers: [
                    InventoryItemService
                ]
            })
            .overrideTemplate(InventoryItemDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InventoryItemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InventoryItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new InventoryItem(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.inventoryItem).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
