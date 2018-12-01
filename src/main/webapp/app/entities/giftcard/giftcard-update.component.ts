import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IGiftcard } from 'app/shared/model/giftcard.model';
import { GiftcardService } from './giftcard.service';
import { IGiftcardType } from 'app/shared/model/giftcard-type.model';
import { GiftcardTypeService } from 'app/entities/giftcard-type';

@Component({
    selector: 'jhi-giftcard-update',
    templateUrl: './giftcard-update.component.html'
})
export class GiftcardUpdateComponent implements OnInit {
    giftcard: IGiftcard;
    isSaving: boolean;

    giftcardtypes: IGiftcardType[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private giftcardService: GiftcardService,
        private giftcardTypeService: GiftcardTypeService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ giftcard }) => {
            this.giftcard = giftcard;
        });
        this.giftcardTypeService.query().subscribe(
            (res: HttpResponse<IGiftcardType[]>) => {
                this.giftcardtypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.giftcard.id !== undefined) {
            this.subscribeToSaveResponse(this.giftcardService.update(this.giftcard));
        } else {
            this.subscribeToSaveResponse(this.giftcardService.create(this.giftcard));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGiftcard>>) {
        result.subscribe((res: HttpResponse<IGiftcard>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackGiftcardTypeById(index: number, item: IGiftcardType) {
        return item.id;
    }
}
