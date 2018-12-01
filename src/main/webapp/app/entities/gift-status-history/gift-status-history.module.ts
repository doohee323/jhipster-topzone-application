import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterTzApplicationSharedModule } from 'app/shared';
import {
    GiftStatusHistoryComponent,
    GiftStatusHistoryDetailComponent,
    GiftStatusHistoryUpdateComponent,
    GiftStatusHistoryDeletePopupComponent,
    GiftStatusHistoryDeleteDialogComponent,
    giftStatusHistoryRoute,
    giftStatusHistoryPopupRoute
} from './';

const ENTITY_STATES = [...giftStatusHistoryRoute, ...giftStatusHistoryPopupRoute];

@NgModule({
    imports: [JhipsterTzApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GiftStatusHistoryComponent,
        GiftStatusHistoryDetailComponent,
        GiftStatusHistoryUpdateComponent,
        GiftStatusHistoryDeleteDialogComponent,
        GiftStatusHistoryDeletePopupComponent
    ],
    entryComponents: [
        GiftStatusHistoryComponent,
        GiftStatusHistoryUpdateComponent,
        GiftStatusHistoryDeleteDialogComponent,
        GiftStatusHistoryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterTzApplicationGiftStatusHistoryModule {}
