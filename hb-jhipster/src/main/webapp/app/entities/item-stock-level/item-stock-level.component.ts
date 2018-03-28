import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ItemStockLevel } from './item-stock-level.model';
import { ItemStockLevelService } from './item-stock-level.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-item-stock-level',
    templateUrl: './item-stock-level.component.html'
})
export class ItemStockLevelComponent implements OnInit, OnDestroy {
itemStockLevels: ItemStockLevel[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private itemStockLevelService: ItemStockLevelService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.itemStockLevelService.query().subscribe(
            (res: HttpResponse<ItemStockLevel[]>) => {
                this.itemStockLevels = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInItemStockLevels();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ItemStockLevel) {
        return item.id;
    }
    registerChangeInItemStockLevels() {
        this.eventSubscriber = this.eventManager.subscribe('itemStockLevelListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
