/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { HelloBeerTestModule } from '../../../test.module';
import { InventoryItemDialogComponent } from '../../../../../../main/webapp/app/entities/inventory-item/inventory-item-dialog.component';
import { InventoryItemService } from '../../../../../../main/webapp/app/entities/inventory-item/inventory-item.service';
import { InventoryItem } from '../../../../../../main/webapp/app/entities/inventory-item/inventory-item.model';
import { BeerService } from '../../../../../../main/webapp/app/entities/beer';

describe('Component Tests', () => {

    describe('InventoryItem Management Dialog Component', () => {
        let comp: InventoryItemDialogComponent;
        let fixture: ComponentFixture<InventoryItemDialogComponent>;
        let service: InventoryItemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HelloBeerTestModule],
                declarations: [InventoryItemDialogComponent],
                providers: [
                    BeerService,
                    InventoryItemService
                ]
            })
            .overrideTemplate(InventoryItemDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InventoryItemDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InventoryItemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new InventoryItem(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.inventoryItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'inventoryItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new InventoryItem();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.inventoryItem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'inventoryItemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
