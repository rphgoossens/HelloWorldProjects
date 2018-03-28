import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Brewery } from './brewery.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Brewery>;

@Injectable()
export class BreweryService {

    private resourceUrl =  SERVER_API_URL + 'api/breweries';

    constructor(private http: HttpClient) { }

    create(brewery: Brewery): Observable<EntityResponseType> {
        const copy = this.convert(brewery);
        return this.http.post<Brewery>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(brewery: Brewery): Observable<EntityResponseType> {
        const copy = this.convert(brewery);
        return this.http.put<Brewery>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Brewery>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Brewery[]>> {
        const options = createRequestOption(req);
        return this.http.get<Brewery[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Brewery[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Brewery = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Brewery[]>): HttpResponse<Brewery[]> {
        const jsonResponse: Brewery[] = res.body;
        const body: Brewery[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Brewery.
     */
    private convertItemFromServer(brewery: Brewery): Brewery {
        const copy: Brewery = Object.assign({}, brewery);
        return copy;
    }

    /**
     * Convert a Brewery to a JSON which can be sent to the server.
     */
    private convert(brewery: Brewery): Brewery {
        const copy: Brewery = Object.assign({}, brewery);
        return copy;
    }
}
