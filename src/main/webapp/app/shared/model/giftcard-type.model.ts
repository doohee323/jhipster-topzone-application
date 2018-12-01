import { IStore } from 'app/shared/model//store.model';
import { IGiftcard } from 'app/shared/model//giftcard.model';

export interface IGiftcardType {
    id?: number;
    name?: string;
    storeId?: number;
    store?: IStore;
    giftcardTypeIds?: IGiftcard[];
}

export class GiftcardType implements IGiftcardType {
    constructor(
        public id?: number,
        public name?: string,
        public storeId?: number,
        public store?: IStore,
        public giftcardTypeIds?: IGiftcard[]
    ) {}
}
