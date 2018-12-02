export interface IStore {
    id?: number;
    country?: string;
    name?: string;
}

export class Store implements IStore {
    constructor(public id?: number, public country?: string, public name?: string) {}
}
