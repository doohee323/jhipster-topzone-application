import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IGiftcardType } from 'app/shared/model/giftcard-type.model';
import { GiftcardTypeService } from './giftcard-type.service';
import { IStore } from 'app/shared/model/store.model';
import { StoreService } from 'app/entities/store';

@Component({
    selector: 'jhi-giftcard-type-update',
    templateUrl: './giftcard-type-update.component.html'
})
export class GiftcardTypeUpdateComponent implements OnInit {
    giftcardType: IGiftcardType;
    isSaving: boolean;

    stores: IStore[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private giftcardTypeService: GiftcardTypeService,
        private storeService: StoreService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ giftcardType }) => {
            this.giftcardType = giftcardType;
        });
        this.storeService.query().subscribe(
            (res: HttpResponse<IStore[]>) => {
                this.stores = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.giftcardType.id !== undefined) {
            this.subscribeToSaveResponse(this.giftcardTypeService.update(this.giftcardType));
        } else {
            this.subscribeToSaveResponse(this.giftcardTypeService.create(this.giftcardType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGiftcardType>>) {
        result.subscribe((res: HttpResponse<IGiftcardType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackStoreById(index: number, item: IStore) {
        return item.id;
    }
}
