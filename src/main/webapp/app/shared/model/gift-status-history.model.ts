import { Moment } from 'moment';
import { IGift } from 'app/shared/model//gift.model';

export interface IGiftStatusHistory {
    id?: number;
    giftStatus?: string;
    createdAt?: Moment;
    giftId?: number;
    createdById?: number;
    gift?: IGift;
}

export class GiftStatusHistory implements IGiftStatusHistory {
    constructor(
        public id?: number,
        public giftStatus?: string,
        public createdAt?: Moment,
        public giftId?: number,
        public createdById?: number,
        public gift?: IGift
    ) {}
}
