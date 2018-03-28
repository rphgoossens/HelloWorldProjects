/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { HelloBeerTestModule } from '../../../test.module';
import { ItemStockLevelDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/item-stock-level/item-stock-level-delete-dialog.component';
import { ItemStockLevelService } from '../../../../../../main/webapp/app/entities/item-stock-level/item-stock-level.service';

describe('Component Tests', () => {

    describe('ItemStockLevel Management Delete Component', () => {
        let comp: ItemStockLevelDeleteDialogComponent;
        let fixture: ComponentFixture<ItemStockLevelDeleteDialogComponent>;
        let service: ItemStockLevelService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [HelloBeerTestModule],
                declarations: [ItemStockLevelDeleteDialogComponent],
                providers: [
                    ItemStockLevelService
                ]
            })
            .overrideTemplate(ItemStockLevelDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ItemStockLevelDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ItemStockLevelService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
