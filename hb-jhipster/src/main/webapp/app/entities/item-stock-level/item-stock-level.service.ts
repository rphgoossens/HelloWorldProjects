import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ItemStockLevel } from './item-stock-level.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ItemStockLevel>;

@Injectable()
export class ItemStockLevelService {

    private resourceUrl =  SERVER_API_URL + 'api/item-stock-levels';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(itemStockLevel: ItemStockLevel): Observable<EntityResponseType> {
        const copy = this.convert(itemStockLevel);
        return this.http.post<ItemStockLevel>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(itemStockLevel: ItemStockLevel): Observable<EntityResponseType> {
        const copy = this.convert(itemStockLevel);
        return this.http.put<ItemStockLevel>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ItemStockLevel>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ItemStockLevel[]>> {
        const options = createRequestOption(req);
        return this.http.get<ItemStockLevel[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ItemStockLevel[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ItemStockLevel = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ItemStockLevel[]>): HttpResponse<ItemStockLevel[]> {
        const jsonResponse: ItemStockLevel[] = res.body;
        const body: ItemStockLevel[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ItemStockLevel.
     */
    private convertItemFromServer(itemStockLevel: ItemStockLevel): ItemStockLevel {
        const copy: ItemStockLevel = Object.assign({}, itemStockLevel);
        copy.stockDate = this.dateUtils
            .convertDateTimeFromServer(itemStockLevel.stockDate);
        return copy;
    }

    /**
     * Convert a ItemStockLevel to a JSON which can be sent to the server.
     */
    private convert(itemStockLevel: ItemStockLevel): ItemStockLevel {
        const copy: ItemStockLevel = Object.assign({}, itemStockLevel);

        // copy.stockDate = this.dateUtils.toDate(itemStockLevel.stockDate);
        return copy;
    }
}
