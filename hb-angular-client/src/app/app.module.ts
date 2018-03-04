import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from '@angular/common/http';

import {AppComponent} from './app.component';
import {EditBeerComponent} from './edit-beer/edit-beer.component';

import {ApiClientService} from './services/beer/index';


@NgModule({
  declarations: [
    AppComponent,
    EditBeerComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [ApiClientService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
