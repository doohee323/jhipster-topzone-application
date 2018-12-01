import { IGiftcardType } from 'app/shared/model//giftcard-type.model';
import { IGift } from 'app/shared/model//gift.model';

export interface IGiftcard {
    id?: number;
    name?: string;
    amount?: number;
    unit?: string;
    giftcardTypeId?: number;
    giftcardType?: IGiftcardType;
    giftcardIds?: IGift[];
}

export class Giftcard implements IGiftcard {
    constructor(
        public id?: number,
        public name?: string,
        public amount?: number,
        public unit?: string,
        public giftcardTypeId?: number,
        public giftcardType?: IGiftcardType,
        public giftcardIds?: IGift[]
    ) {}
}
