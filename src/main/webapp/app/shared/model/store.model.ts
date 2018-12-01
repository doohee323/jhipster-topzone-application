import { IGiftcardType } from 'app/shared/model//giftcard-type.model';

export interface IStore {
    id?: number;
    country?: string;
    name?: string;
    storeIds?: IGiftcardType[];
}

export class Store implements IStore {
    constructor(public id?: number, public country?: string, public name?: string, public storeIds?: IGiftcardType[]) {}
}
