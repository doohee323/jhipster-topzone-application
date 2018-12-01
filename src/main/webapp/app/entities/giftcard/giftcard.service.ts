import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGiftcard } from 'app/shared/model/giftcard.model';

type EntityResponseType = HttpResponse<IGiftcard>;
type EntityArrayResponseType = HttpResponse<IGiftcard[]>;

@Injectable({ providedIn: 'root' })
export class GiftcardService {
    public resourceUrl = SERVER_API_URL + 'api/giftcards';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/giftcards';

    constructor(private http: HttpClient) {}

    create(giftcard: IGiftcard): Observable<EntityResponseType> {
        return this.http.post<IGiftcard>(this.resourceUrl, giftcard, { observe: 'response' });
    }

    update(giftcard: IGiftcard): Observable<EntityResponseType> {
        return this.http.put<IGiftcard>(this.resourceUrl, giftcard, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGiftcard>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGiftcard[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGiftcard[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
