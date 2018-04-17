import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { ItemStockLevel } from './item-stock-level.model';
import { ItemStockLevelService } from './item-stock-level.service';

@Injectable()
export class ItemStockLevelPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private itemStockLevelService: ItemStockLevelService

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
                this.itemStockLevelService.find(id)
                    .subscribe((itemStockLevelResponse: HttpResponse<ItemStockLevel>) => {
                        const itemStockLevel: ItemStockLevel = itemStockLevelResponse.body;
                        itemStockLevel.stockDate = this.datePipe
                            .transform(itemStockLevel.stockDate, 'yyyy-MM-dd HH:mm');
                        this.ngbModalRef = this.itemStockLevelModalRef(component, itemStockLevel);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.itemStockLevelModalRef(component, new ItemStockLevel());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    itemStockLevelModalRef(component: Component, itemStockLevel: ItemStockLevel): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.itemStockLevel = itemStockLevel;
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
