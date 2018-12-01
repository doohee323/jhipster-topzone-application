import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Recipient } from 'app/shared/model/recipient.model';
import { RecipientService } from './recipient.service';
import { RecipientComponent } from './recipient.component';
import { RecipientDetailComponent } from './recipient-detail.component';
import { RecipientUpdateComponent } from './recipient-update.component';
import { RecipientDeletePopupComponent } from './recipient-delete-dialog.component';
import { IRecipient } from 'app/shared/model/recipient.model';

@Injectable({ providedIn: 'root' })
export class RecipientResolve implements Resolve<IRecipient> {
    constructor(private service: RecipientService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Recipient> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Recipient>) => response.ok),
                map((recipient: HttpResponse<Recipient>) => recipient.body)
            );
        }
        return of(new Recipient());
    }
}

export const recipientRoute: Routes = [
    {
        path: 'recipient',
        component: RecipientComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'jhipsterTzApplicationApp.recipient.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recipient/:id/view',
        component: RecipientDetailComponent,
        resolve: {
            recipient: RecipientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.recipient.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recipient/new',
        component: RecipientUpdateComponent,
        resolve: {
            recipient: RecipientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.recipient.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'recipient/:id/edit',
        component: RecipientUpdateComponent,
        resolve: {
            recipient: RecipientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.recipient.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const recipientPopupRoute: Routes = [
    {
        path: 'recipient/:id/delete',
        component: RecipientDeletePopupComponent,
        resolve: {
            recipient: RecipientResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'jhipsterTzApplicationApp.recipient.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
