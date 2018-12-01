import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Gift } from 'app/shared/model/gift.model';
import { GiftService } from './gift.service';
import { GiftComponent } from './gift.component';
import { GiftDetailComponent } from './gift-detail.component';
import { GiftUpdateComponent } from './gift-update.component';
import { GiftDeletePopupComponent } from './gift-delete-dialog.component';
import { IGift } from 'app/shared/model/gift.model';

@Injectable({ providedIn: 'root' })
export class GiftResolve implements Resolve<IGift> {
    constructor(private service: GiftService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Gift> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Gift>) => response.ok),
                map((gift: HttpResponse<Gift>) => gift.body)
            );
        }
        return of(new Gift());
    }
}

export const giftRoute: Routes = [
    {
        path: 'gift',
        component: GiftComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jhipsterTzApplicationApp.gift.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gift/:id/view',
        component: GiftDetailComponent,
        resolve: {
            gift: GiftResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.gift.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gift/new',
        component: GiftUpdateComponent,
        resolve: {
            gift: GiftResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.gift.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'gift/:id/edit',
        component: GiftUpdateComponent,
        resolve: {
            gift: GiftResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.gift.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const giftPopupRoute: Routes = [
    {
        path: 'gift/:id/delete',
        component: GiftDeletePopupComponent,
        resolve: {
            gift: GiftResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.gift.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
