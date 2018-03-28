import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ItemStockLevel } from './item-stock-level.model';
import { ItemStockLevelPopupService } from './item-stock-level-popup.service';
import { ItemStockLevelService } from './item-stock-level.service';

@Component({
    selector: 'jhi-item-stock-level-delete-dialog',
    templateUrl: './item-stock-level-delete-dialog.component.html'
})
export class ItemStockLevelDeleteDialogComponent {

    itemStockLevel: ItemStockLevel;

    constructor(
        private itemStockLevelService: ItemStockLevelService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.itemStockLevelService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'itemStockLevelListModification',
                content: 'Deleted an itemStockLevel'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-item-stock-level-delete-popup',
    template: ''
})
export class ItemStockLevelDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private itemStockLevelPopupService: ItemStockLevelPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.itemStockLevelPopupService
                .open(ItemStockLevelDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
