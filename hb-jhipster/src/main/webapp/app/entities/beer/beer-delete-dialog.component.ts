import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Beer } from './beer.model';
import { BeerPopupService } from './beer-popup.service';
import { BeerService } from './beer.service';

@Component({
    selector: 'jhi-beer-delete-dialog',
    templateUrl: './beer-delete-dialog.component.html'
})
export class BeerDeleteDialogComponent {

    beer: Beer;

    constructor(
        private beerService: BeerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.beerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'beerListModification',
                content: 'Deleted an beer'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-beer-delete-popup',
    template: ''
})
export class BeerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private beerPopupService: BeerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.beerPopupService
                .open(BeerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
