import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Giftcard } from 'app/shared/model/giftcard.model';
import { GiftcardService } from './giftcard.service';
import { GiftcardComponent } from './giftcard.component';
import { GiftcardDetailComponent } from './giftcard-detail.component';
import { GiftcardUpdateComponent } from './giftcard-update.component';
import { GiftcardDeletePopupComponent } from './giftcard-delete-dialog.component';
import { IGiftcard } from 'app/shared/model/giftcard.model';

@Injectable({ providedIn: 'root' })
export class GiftcardResolve implements Resolve<IGiftcard> {
    constructor(private service: GiftcardService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Giftcard> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Giftcard>) => response.ok),
                map((giftcard: HttpResponse<Giftcard>) => giftcard.body)
            );
        }
        return of(new Giftcard());
    }
}

export const giftcardRoute: Routes = [
    {
        path: 'giftcard',
        component: GiftcardComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.giftcard.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'giftcard/:id/view',
        component: GiftcardDetailComponent,
        resolve: {
            giftcard: GiftcardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.giftcard.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'giftcard/new',
        component: GiftcardUpdateComponent,
        resolve: {
            giftcard: GiftcardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.giftcard.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'giftcard/:id/edit',
        component: GiftcardUpdateComponent,
        resolve: {
            giftcard: GiftcardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.giftcard.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const giftcardPopupRoute: Routes = [
    {
        path: 'giftcard/:id/delete',
        component: GiftcardDeletePopupComponent,
        resolve: {
            giftcard: GiftcardResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.giftcard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
