import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterTzApplicationSharedModule } from 'app/shared';
import {
    GiftcardTypeComponent,
    GiftcardTypeDetailComponent,
    GiftcardTypeUpdateComponent,
    GiftcardTypeDeletePopupComponent,
    GiftcardTypeDeleteDialogComponent,
    giftcardTypeRoute,
    giftcardTypePopupRoute
} from './';

const ENTITY_STATES = [...giftcardTypeRoute, ...giftcardTypePopupRoute];

@NgModule({
    imports: [JhipsterTzApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GiftcardTypeComponent,
        GiftcardTypeDetailComponent,
        GiftcardTypeUpdateComponent,
        GiftcardTypeDeleteDialogComponent,
        GiftcardTypeDeletePopupComponent
    ],
    entryComponents: [
        GiftcardTypeComponent,
        GiftcardTypeUpdateComponent,
        GiftcardTypeDeleteDialogComponent,
        GiftcardTypeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterTzApplicationGiftcardTypeModule {}
