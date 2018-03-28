/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HelloBeerTestModule } from '../../../test.module';
import { InventoryItemComponent } from '../../../../../../main/webapp/app/entities/inventory-item/inventory-item.component';
import { InventoryItemService } from '../../../../../../main/webapp/app/entities/inventory-item/inventory-item.service';
import { InventoryItem } from '../../../../../../main/webapp/app/entities/inventory-item/inventory-item.model';

describe('Component Tests', () => {

    describe('InventoryItem Management Component', () => {
        let comp: InventoryItemComponent;
        let fixture: ComponentFixture<InventoryItemComponent>;
        let service: InventoryItemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HelloBeerTestModule],
                declarations: [InventoryItemComponent],
                providers: [
                    InventoryItemService
                ]
            })
            .overrideTemplate(InventoryItemComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InventoryItemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InventoryItemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new InventoryItem(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.inventoryItems[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
