import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { InventoryItem } from './inventory-item.model';
import { InventoryItemPopupService } from './inventory-item-popup.service';
import { InventoryItemService } from './inventory-item.service';

@Component({
    selector: 'jhi-inventory-item-delete-dialog',
    templateUrl: './inventory-item-delete-dialog.component.html'
})
export class InventoryItemDeleteDialogComponent {

    inventoryItem: InventoryItem;

    constructor(
        private inventoryItemService: InventoryItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.inventoryItemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'inventoryItemListModification',
                content: 'Deleted an inventoryItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-inventory-item-delete-popup',
    template: ''
})
export class InventoryItemDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inventoryItemPopupService: InventoryItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.inventoryItemPopupService
                .open(InventoryItemDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
