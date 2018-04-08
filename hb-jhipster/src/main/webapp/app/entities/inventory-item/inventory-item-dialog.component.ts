import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {JhiAlertService, JhiEventManager} from 'ng-jhipster';

import {InventoryItem} from './inventory-item.model';
import {InventoryItemPopupService} from './inventory-item-popup.service';
import {InventoryItemService} from './inventory-item.service';
import {Beer, BeerService} from '../beer';

@Component({
    selector: 'jhi-inventory-item-dialog',
    templateUrl: './inventory-item-dialog.component.html'
})
export class InventoryItemDialogComponent implements OnInit {

    inventoryItem: InventoryItem;
    isSaving: boolean;

    beers: Beer[];
    beerOptions: any[];

    constructor(public activeModal: NgbActiveModal,
                private jhiAlertService: JhiAlertService,
                private inventoryItemService: InventoryItemService,
                private beerService: BeerService,
                private eventManager: JhiEventManager) {
    }

    search(event) {
        this.beerOptions = this.beers.filter((beer) => beer.name.startsWith(event.query));
    }

    ngOnInit() {
        this.isSaving = false;
        this.beerService.query()
            .subscribe((res: HttpResponse<Beer[]>) => { this.beers = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.inventoryItem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.inventoryItemService.update(this.inventoryItem));
        } else {
            this.subscribeToSaveResponse(
                this.inventoryItemService.create(this.inventoryItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<InventoryItem>>) {
        result.subscribe((res: HttpResponse<InventoryItem>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: InventoryItem) {
        this.eventManager.broadcast({name: 'inventoryItemListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBeerById(index: number, item: Beer) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-inventory-item-popup',
    template: ''
})
export class InventoryItemPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(private route: ActivatedRoute,
                private inventoryItemPopupService: InventoryItemPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.inventoryItemPopupService
                    .open(InventoryItemDialogComponent as Component, params['id']);
            } else {
                this.inventoryItemPopupService
                    .open(InventoryItemDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
