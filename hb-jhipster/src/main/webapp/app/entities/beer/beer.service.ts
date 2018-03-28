import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Beer } from './beer.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Beer>;

@Injectable()
export class BeerService {

    private resourceUrl =  SERVER_API_URL + 'api/beers';

    constructor(private http: HttpClient) { }

    create(beer: Beer): Observable<EntityResponseType> {
        const copy = this.convert(beer);
        return this.http.post<Beer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(beer: Beer): Observable<EntityResponseType> {
        const copy = this.convert(beer);
        return this.http.put<Beer>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Beer>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Beer[]>> {
        const options = createRequestOption(req);
        return this.http.get<Beer[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Beer[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Beer = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Beer[]>): HttpResponse<Beer[]> {
        const jsonResponse: Beer[] = res.body;
        const body: Beer[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Beer.
     */
    private convertItemFromServer(beer: Beer): Beer {
        const copy: Beer = Object.assign({}, beer);
        return copy;
    }

    /**
     * Convert a Beer to a JSON which can be sent to the server.
     */
    private convert(beer: Beer): Beer {
        const copy: Beer = Object.assign({}, beer);
        return copy;
    }
}
