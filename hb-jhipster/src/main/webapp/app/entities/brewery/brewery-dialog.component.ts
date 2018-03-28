import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Brewery } from './brewery.model';
import { BreweryPopupService } from './brewery-popup.service';
import { BreweryService } from './brewery.service';

@Component({
    selector: 'jhi-brewery-dialog',
    templateUrl: './brewery-dialog.component.html'
})
export class BreweryDialogComponent implements OnInit {

    brewery: Brewery;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private breweryService: BreweryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.brewery.id !== undefined) {
            this.subscribeToSaveResponse(
                this.breweryService.update(this.brewery));
        } else {
            this.subscribeToSaveResponse(
                this.breweryService.create(this.brewery));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Brewery>>) {
        result.subscribe((res: HttpResponse<Brewery>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Brewery) {
        this.eventManager.broadcast({ name: 'breweryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-brewery-popup',
    template: ''
})
export class BreweryPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private breweryPopupService: BreweryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.breweryPopupService
                    .open(BreweryDialogComponent as Component, params['id']);
            } else {
                this.breweryPopupService
                    .open(BreweryDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
