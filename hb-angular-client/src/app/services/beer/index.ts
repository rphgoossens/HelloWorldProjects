import { Inject, Injectable, Optional } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import {
  Type,
  Beer
} from './models';

/**
* Created with angular4-swagger-client-generator.
*/
@Injectable()
export class ApiClientService {

  private domain = 'http://localhost:8080';

  constructor(private http: HttpClient, @Optional() @Inject('domain') domain: string ) {
    if (domain) {
      this.domain = domain;
    }
  }

  /**
  * Method addToBeerRepositoryUsingPOST
  * @param beer beer
  * @return Full HTTP response as Observable
  */
  public addToBeerRepositoryUsingPOST(beer: Beer): Observable<HttpResponse<Beer>> {
    let uri = `/hello-beer/1.0/beer`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    return this.sendRequest<Beer>('post', uri, headers, params, JSON.stringify(beer));
  }

  /**
  * Method getAllBeersUsingGET
  * @param type type
  * @return Full HTTP response as Observable
  */
  public getAllBeersUsingGET(type: string): Observable<HttpResponse<Beer[]>> {
    let uri = `/hello-beer/1.0/beers`;
    let headers = new HttpHeaders();
    let params = new HttpParams();
    if (type !== undefined && type !== null) {
      params = params.set('type', type + '');
    }
    return this.sendRequest<Beer[]>('get', uri, headers, params, null);
  }

  private sendRequest<T>(method: string, uri: string, headers: HttpHeaders, params: HttpParams, body: any): Observable<HttpResponse<T>> {
    if (method === 'get') {
      return this.http.get<T>(this.domain + uri, { headers: headers.set('Accept', 'application/json'), params: params, observe: 'response' });
    } else if (method === 'put') {
      return this.http.put<T>(this.domain + uri, body, { headers: headers.set('Content-Type', 'application/json'), params: params, observe: 'response' });
    } else if (method === 'post') {
      return this.http.post<T>(this.domain + uri, body, { headers: headers.set('Content-Type', 'application/json'), params: params, observe: 'response' });
    } else if (method === 'delete') {
      return this.http.delete<T>(this.domain + uri, { headers: headers, params: params, observe: 'response' });
    } else {
      console.error('Unsupported request: ' + method);
      return Observable.throw('Unsupported request: ' + method);
    }
  }
}
