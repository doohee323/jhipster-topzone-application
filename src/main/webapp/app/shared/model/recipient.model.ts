import { Moment } from 'moment';
import { IGift } from 'app/shared/model//gift.model';

export interface IRecipient {
    id?: number;
    name?: string;
    phoneNumber?: string;
    email?: string;
    createdAt?: Moment;
    userId?: number;
    recipientIds?: IGift[];
}

export class Recipient implements IRecipient {
    constructor(
        public id?: number,
        public name?: string,
        public phoneNumber?: string,
        public email?: string,
        public createdAt?: Moment,
        public userId?: number,
        public recipientIds?: IGift[]
    ) {}
}
