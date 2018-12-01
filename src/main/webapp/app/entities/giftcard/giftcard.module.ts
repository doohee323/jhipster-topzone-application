import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterTzApplicationSharedModule } from 'app/shared';
import {
    GiftcardComponent,
    GiftcardDetailComponent,
    GiftcardUpdateComponent,
    GiftcardDeletePopupComponent,
    GiftcardDeleteDialogComponent,
    giftcardRoute,
    giftcardPopupRoute
} from './';

const ENTITY_STATES = [...giftcardRoute, ...giftcardPopupRoute];

@NgModule({
    imports: [JhipsterTzApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GiftcardComponent,
        GiftcardDetailComponent,
        GiftcardUpdateComponent,
        GiftcardDeleteDialogComponent,
        GiftcardDeletePopupComponent
    ],
    entryComponents: [GiftcardComponent, GiftcardUpdateComponent, GiftcardDeleteDialogComponent, GiftcardDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterTzApplicationGiftcardModule {}
