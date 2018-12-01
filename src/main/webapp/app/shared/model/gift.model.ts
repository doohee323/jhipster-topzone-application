import { Moment } from 'moment';
import { IGiftcard } from 'app/shared/model//giftcard.model';
import { IRecipient } from 'app/shared/model//recipient.model';
import { IGiftStatusHistory } from 'app/shared/model//gift-status-history.model';

export interface IGift {
    id?: number;
    amount?: number;
    orderedAt?: Moment;
    fee?: number;
    exchangeRate?: number;
    totalAmount?: number;
    sendFrom?: string;
    message?: string;
    referenceNumber?: string;
    depositor?: string;
    displayAt?: string;
    senderId?: number;
    recipientId?: number;
    giftcardId?: number;
    giftcard?: IGiftcard;
    recipient?: IRecipient;
    giftIds?: IGiftStatusHistory[];
}

export class Gift implements IGift {
    constructor(
        public id?: number,
        public amount?: number,
        public orderedAt?: Moment,
        public fee?: number,
        public exchangeRate?: number,
        public totalAmount?: number,
        public sendFrom?: string,
        public message?: string,
        public referenceNumber?: string,
        public depositor?: string,
        public displayAt?: string,
        public senderId?: number,
        public recipientId?: number,
        public giftcardId?: number,
        public giftcard?: IGiftcard,
        public recipient?: IRecipient,
        public giftIds?: IGiftStatusHistory[]
    ) {}
}
