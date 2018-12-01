import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { GiftStatusHistory } from 'app/shared/model/gift-status-history.model';
import { GiftStatusHistoryService } from './gift-status-history.service';
import { GiftStatusHistoryComponent } from './gift-status-history.component';
import { GiftStatusHistoryDetailComponent } from './gift-status-history-detail.component';
import { GiftStatusHistoryUpdateComponent } from './gift-status-history-update.component';
import { GiftStatusHistoryDeletePopupComponent } from './gift-status-history-delete-dialog.component';
import { IGiftStatusHistory } from 'app/shared/model/gift-status-history.model';

@Injectable({ providedIn: 'root' })
export class GiftStatusHistoryResolve implements Resolve<IGiftStatusHistory> {
    constructor(private service: GiftStatusHistoryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<GiftStatusHistory> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<GiftStatusHistory>) => response.ok),
                map((giftStatusHistory: HttpResponse<GiftStatusHistory>) => giftStatusHistory.body)
            );
        }
        return of(new GiftStatusHistory());
    }
}

export const giftStatusHistoryRoute: Routes = [
    {
        path: 'gift-status-history',
        component: GiftStatusHistoryComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jhipsterTzApplicationApp.giftStatusHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gift-status-history/:id/view',
        component: GiftStatusHistoryDetailComponent,
        resolve: {
            giftStatusHistory: GiftStatusHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.giftStatusHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gift-status-history/new',
        component: GiftStatusHistoryUpdateComponent,
        resolve: {
            giftStatusHistory: GiftStatusHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.giftStatusHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gift-status-history/:id/edit',
        component: GiftStatusHistoryUpdateComponent,
        resolve: {
            giftStatusHistory: GiftStatusHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.giftStatusHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const giftStatusHistoryPopupRoute: Routes = [
    {
        path: 'gift-status-history/:id/delete',
        component: GiftStatusHistoryDeletePopupComponent,
        resolve: {
            giftStatusHistory: GiftStatusHistoryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.giftStatusHistory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
