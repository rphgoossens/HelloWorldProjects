import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Brewery } from './brewery.model';
import { BreweryService } from './brewery.service';

@Component({
    selector: 'jhi-brewery-detail',
    templateUrl: './brewery-detail.component.html'
})
export class BreweryDetailComponent implements OnInit, OnDestroy {

    brewery: Brewery;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private breweryService: BreweryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBreweries();
    }

    load(id) {
        this.breweryService.find(id)
            .subscribe((breweryResponse: HttpResponse<Brewery>) => {
                this.brewery = breweryResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBreweries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'breweryListModification',
            (response) => this.load(this.brewery.id)
        );
    }
}
