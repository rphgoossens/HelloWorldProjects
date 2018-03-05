import { Component, OnInit } from '@angular/core';

import {ApiClientService} from './services/beer/index';
import {Beer} from './services/beer/models';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  public beers: Beer[];

  constructor(private apiClientService: ApiClientService, private translateService: TranslateService) {
    translateService.setDefaultLang('en');
  }

  public onBeerPosted(beer: Beer): void {
    this.beers.push(beer);
  }

  ngOnInit() {
    this.apiClientService.getAllBeersUsingGET(null)
      .subscribe(resp => {
        this.beers = resp.body;
      }, error => {
        console.log(error);
      });
  }

}
