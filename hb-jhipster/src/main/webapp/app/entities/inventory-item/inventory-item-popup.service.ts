import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { InventoryItem } from './inventory-item.model';
import { InventoryItemService } from './inventory-item.service';

@Injectable()
export class InventoryItemPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private inventoryItemService: InventoryItemService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.inventoryItemService.find(id)
                    .subscribe((inventoryItemResponse: HttpResponse<InventoryItem>) => {
                        const inventoryItem: InventoryItem = inventoryItemResponse.body;
                        this.ngbModalRef = this.inventoryItemModalRef(component, inventoryItem);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.inventoryItemModalRef(component, new InventoryItem());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    inventoryItemModalRef(component: Component, inventoryItem: InventoryItem): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.inventoryItem = inventoryItem;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
