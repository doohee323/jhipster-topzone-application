import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGift } from 'app/shared/model/gift.model';

type EntityResponseType = HttpResponse<IGift>;
type EntityArrayResponseType = HttpResponse<IGift[]>;

@Injectable({ providedIn: 'root' })
export class GiftService {
    public resourceUrl = SERVER_API_URL + 'api/gifts';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/gifts';

    constructor(private http: HttpClient) {}

    create(gift: IGift): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(gift);
        return this.http
            .post<IGift>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(gift: IGift): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(gift);
        return this.http
            .put<IGift>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IGift>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IGift[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IGift[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(gift: IGift): IGift {
        const copy: IGift = Object.assign({}, gift, {
            orderedAt: gift.orderedAt != null && gift.orderedAt.isValid() ? gift.orderedAt.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.orderedAt = res.body.orderedAt != null ? moment(res.body.orderedAt) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((gift: IGift) => {
                gift.orderedAt = gift.orderedAt != null ? moment(gift.orderedAt) : null;
            });
        }
        return res;
    }
}
