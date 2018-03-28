import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Beer } from './beer.model';
import { BeerPopupService } from './beer-popup.service';
import { BeerService } from './beer.service';
import { Brewery, BreweryService } from '../brewery';

@Component({
    selector: 'jhi-beer-dialog',
    templateUrl: './beer-dialog.component.html'
})
export class BeerDialogComponent implements OnInit {

    beer: Beer;
    isSaving: boolean;

    breweries: Brewery[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private beerService: BeerService,
        private breweryService: BreweryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.breweryService.query()
            .subscribe((res: HttpResponse<Brewery[]>) => { this.breweries = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.beer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.beerService.update(this.beer));
        } else {
            this.subscribeToSaveResponse(
                this.beerService.create(this.beer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Beer>>) {
        result.subscribe((res: HttpResponse<Beer>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Beer) {
        this.eventManager.broadcast({ name: 'beerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBreweryById(index: number, item: Brewery) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-beer-popup',
    template: ''
})
export class BeerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private beerPopupService: BeerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.beerPopupService
                    .open(BeerDialogComponent as Component, params['id']);
            } else {
                this.beerPopupService
                    .open(BeerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
