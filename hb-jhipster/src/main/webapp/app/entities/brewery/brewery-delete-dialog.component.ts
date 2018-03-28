import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Brewery } from './brewery.model';
import { BreweryPopupService } from './brewery-popup.service';
import { BreweryService } from './brewery.service';

@Component({
    selector: 'jhi-brewery-delete-dialog',
    templateUrl: './brewery-delete-dialog.component.html'
})
export class BreweryDeleteDialogComponent {

    brewery: Brewery;

    constructor(
        private breweryService: BreweryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.breweryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'breweryListModification',
                content: 'Deleted an brewery'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-brewery-delete-popup',
    template: ''
})
export class BreweryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private breweryPopupService: BreweryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.breweryPopupService
                .open(BreweryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
