import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ItemStockLevel } from './item-stock-level.model';
import { ItemStockLevelService } from './item-stock-level.service';

@Component({
    selector: 'jhi-item-stock-level-detail',
    templateUrl: './item-stock-level-detail.component.html'
})
export class ItemStockLevelDetailComponent implements OnInit, OnDestroy {

    itemStockLevel: ItemStockLevel;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private itemStockLevelService: ItemStockLevelService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInItemStockLevels();
    }

    load(id) {
        this.itemStockLevelService.find(id)
            .subscribe((itemStockLevelResponse: HttpResponse<ItemStockLevel>) => {
                this.itemStockLevel = itemStockLevelResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInItemStockLevels() {
        this.eventSubscriber = this.eventManager.subscribe(
            'itemStockLevelListModification',
            (response) => this.load(this.itemStockLevel.id)
        );
    }
}
