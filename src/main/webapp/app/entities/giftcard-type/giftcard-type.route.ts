import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { GiftcardType } from 'app/shared/model/giftcard-type.model';
import { GiftcardTypeService } from './giftcard-type.service';
import { GiftcardTypeComponent } from './giftcard-type.component';
import { GiftcardTypeDetailComponent } from './giftcard-type-detail.component';
import { GiftcardTypeUpdateComponent } from './giftcard-type-update.component';
import { GiftcardTypeDeletePopupComponent } from './giftcard-type-delete-dialog.component';
import { IGiftcardType } from 'app/shared/model/giftcard-type.model';

@Injectable({ providedIn: 'root' })
export class GiftcardTypeResolve implements Resolve<IGiftcardType> {
    constructor(private service: GiftcardTypeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<GiftcardType> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<GiftcardType>) => response.ok),
                map((giftcardType: HttpResponse<GiftcardType>) => giftcardType.body)
            );
        }
        return of(new GiftcardType());
    }
}

export const giftcardTypeRoute: Routes = [
    {
        path: 'giftcard-type',
        component: GiftcardTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.giftcardType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'giftcard-type/:id/view',
        component: GiftcardTypeDetailComponent,
        resolve: {
            giftcardType: GiftcardTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.giftcardType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'giftcard-type/new',
        component: GiftcardTypeUpdateComponent,
        resolve: {
            giftcardType: GiftcardTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.giftcardType.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'giftcard-type/:id/edit',
        component: GiftcardTypeUpdateComponent,
        resolve: {
            giftcardType: GiftcardTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.giftcardType.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const giftcardTypePopupRoute: Routes = [
    {
        path: 'giftcard-type/:id/delete',
        component: GiftcardTypeDeletePopupComponent,
        resolve: {
            giftcardType: GiftcardTypeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.giftcardType.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
