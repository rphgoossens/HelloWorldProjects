import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ItemStockLevel } from './item-stock-level.model';
import { ItemStockLevelPopupService } from './item-stock-level-popup.service';
import { ItemStockLevelService } from './item-stock-level.service';
import { InventoryItem, InventoryItemService } from '../inventory-item';

@Component({
    selector: 'jhi-item-stock-level-dialog',
    templateUrl: './item-stock-level-dialog.component.html'
})
export class ItemStockLevelDialogComponent implements OnInit {

    itemStockLevel: ItemStockLevel;
    isSaving: boolean;

    inventoryitems: InventoryItem[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private itemStockLevelService: ItemStockLevelService,
        private inventoryItemService: InventoryItemService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.inventoryItemService.query()
            .subscribe((res: HttpResponse<InventoryItem[]>) => { this.inventoryitems = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.itemStockLevel.id !== undefined) {
            this.subscribeToSaveResponse(
                this.itemStockLevelService.update(this.itemStockLevel));
        } else {
            this.subscribeToSaveResponse(
                this.itemStockLevelService.create(this.itemStockLevel));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ItemStockLevel>>) {
        result.subscribe((res: HttpResponse<ItemStockLevel>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ItemStockLevel) {
        this.eventManager.broadcast({ name: 'itemStockLevelListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackInventoryItemById(index: number, item: InventoryItem) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-item-stock-level-popup',
    template: ''
})
export class ItemStockLevelPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private itemStockLevelPopupService: ItemStockLevelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.itemStockLevelPopupService
                    .open(ItemStockLevelDialogComponent as Component, params['id']);
            } else {
                this.itemStockLevelPopupService
                    .open(ItemStockLevelDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
