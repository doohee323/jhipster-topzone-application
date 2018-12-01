import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhipsterTzApplicationStoreModule } from './store/store.module';
import { JhipsterTzApplicationGiftcardTypeModule } from './giftcard-type/giftcard-type.module';
import { JhipsterTzApplicationGiftcardModule } from './giftcard/giftcard.module';
import { JhipsterTzApplicationRecipientModule } from './recipient/recipient.module';
import { JhipsterTzApplicationGiftModule } from './gift/gift.module';
import { JhipsterTzApplicationGiftStatusHistoryModule } from './gift-status-history/gift-status-history.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhipsterTzApplicationStoreModule,
        JhipsterTzApplicationGiftcardTypeModule,
        JhipsterTzApplicationGiftcardModule,
        JhipsterTzApplicationRecipientModule,
        JhipsterTzApplicationGiftModule,
        JhipsterTzApplicationGiftStatusHistoryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterTzApplicationEntityModule {}
