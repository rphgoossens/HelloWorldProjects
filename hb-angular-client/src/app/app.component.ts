import { Component, OnInit } from '@angular/core';

import {ApiClientService} from './services/beer/index';
import {Beer} from './services/beer/models';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  public beers: Beer[];

  constructor(private apiClientService: ApiClientService) {
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
