import {Component, EventEmitter, OnInit, Output} from '@angular/core';

import {ApiClientService} from "../services/beer";
import {Beer, Type} from '../services/beer/models';

@Component({
  selector: 'app-edit-beer',
  templateUrl: './edit-beer.component.html',
  styleUrls: ['./edit-beer.component.css']
})
export class EditBeerComponent implements OnInit {

  @Output() onBeerPosted = new EventEmitter<Beer>();

  public beer: Beer = {} as Beer;
  public beerTypes: string[];

  constructor(private apiClientService: ApiClientService) {
    this.beerTypes = (Object.keys(Type));
  }

  /**
   * Call REST service to POST a beer
   */
  public postBeer(): void {
    this.apiClientService.addToBeerRepositoryUsingPOST(this.beer)
      .subscribe(resp => {
        this.pushBeer(resp.body)
      }, (error => {
        console.log(error);
      }));
  }

  private pushBeer(beer: Beer): void {
    // add beer to app
    this.onBeerPosted.emit(beer);
    this.reset();
  }

  private reset(): void {
    this.beer = {} as Beer;
  }

  ngOnInit() {
  }

}
