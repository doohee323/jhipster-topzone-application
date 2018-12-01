import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGiftStatusHistory } from 'app/shared/model/gift-status-history.model';

type EntityResponseType = HttpResponse<IGiftStatusHistory>;
type EntityArrayResponseType = HttpResponse<IGiftStatusHistory[]>;

@Injectable({ providedIn: 'root' })
export class GiftStatusHistoryService {
    public resourceUrl = SERVER_API_URL + 'api/gift-status-histories';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/gift-status-histories';

    constructor(private http: HttpClient) {}

    create(giftStatusHistory: IGiftStatusHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(giftStatusHistory);
        return this.http
            .post<IGiftStatusHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(giftStatusHistory: IGiftStatusHistory): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(giftStatusHistory);
        return this.http
            .put<IGiftStatusHistory>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IGiftStatusHistory>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IGiftStatusHistory[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IGiftStatusHistory[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(giftStatusHistory: IGiftStatusHistory): IGiftStatusHistory {
        const copy: IGiftStatusHistory = Object.assign({}, giftStatusHistory, {
            createdAt:
                giftStatusHistory.createdAt != null && giftStatusHistory.createdAt.isValid()
                    ? giftStatusHistory.createdAt.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.createdAt = res.body.createdAt != null ? moment(res.body.createdAt) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((giftStatusHistory: IGiftStatusHistory) => {
                giftStatusHistory.createdAt = giftStatusHistory.createdAt != null ? moment(giftStatusHistory.createdAt) : null;
            });
        }
        return res;
    }
}
