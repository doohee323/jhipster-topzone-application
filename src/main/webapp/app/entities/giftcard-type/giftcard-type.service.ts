import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGiftcardType } from 'app/shared/model/giftcard-type.model';

type EntityResponseType = HttpResponse<IGiftcardType>;
type EntityArrayResponseType = HttpResponse<IGiftcardType[]>;

@Injectable({ providedIn: 'root' })
export class GiftcardTypeService {
    public resourceUrl = SERVER_API_URL + 'api/giftcard-types';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/giftcard-types';

    constructor(private http: HttpClient) {}

    create(giftcardType: IGiftcardType): Observable<EntityResponseType> {
        return this.http.post<IGiftcardType>(this.resourceUrl, giftcardType, { observe: 'response' });
    }

    update(giftcardType: IGiftcardType): Observable<EntityResponseType> {
        return this.http.put<IGiftcardType>(this.resourceUrl, giftcardType, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGiftcardType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGiftcardType[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGiftcardType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
