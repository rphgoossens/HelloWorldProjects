/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { HelloBeerTestModule } from '../../../test.module';
import { ItemStockLevelDialogComponent } from '../../../../../../main/webapp/app/entities/item-stock-level/item-stock-level-dialog.component';
import { ItemStockLevelService } from '../../../../../../main/webapp/app/entities/item-stock-level/item-stock-level.service';
import { ItemStockLevel } from '../../../../../../main/webapp/app/entities/item-stock-level/item-stock-level.model';
import { InventoryItemService } from '../../../../../../main/webapp/app/entities/inventory-item';

describe('Component Tests', () => {

    describe('ItemStockLevel Management Dialog Component', () => {
        let comp: ItemStockLevelDialogComponent;
        let fixture: ComponentFixture<ItemStockLevelDialogComponent>;
        let service: ItemStockLevelService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HelloBeerTestModule],
                declarations: [ItemStockLevelDialogComponent],
                providers: [
                    InventoryItemService,
                    ItemStockLevelService
                ]
            })
            .overrideTemplate(ItemStockLevelDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ItemStockLevelDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemStockLevelService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ItemStockLevel(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.itemStockLevel = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'itemStockLevelListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ItemStockLevel();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.itemStockLevel = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'itemStockLevelListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
