import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IGift } from 'app/shared/model/gift.model';
import { GiftService } from './gift.service';
import { IGiftcard } from 'app/shared/model/giftcard.model';
import { GiftcardService } from 'app/entities/giftcard';
import { IRecipient } from 'app/shared/model/recipient.model';
import { RecipientService } from 'app/entities/recipient';

@Component({
    selector: 'jhi-gift-update',
    templateUrl: './gift-update.component.html'
})
export class GiftUpdateComponent implements OnInit {
    gift: IGift;
    isSaving: boolean;

    giftcards: IGiftcard[];

    recipients: IRecipient[];
    orderedAtDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private giftService: GiftService,
        private giftcardService: GiftcardService,
        private recipientService: RecipientService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ gift }) => {
            this.gift = gift;
        });
        this.giftcardService.query().subscribe(
            (res: HttpResponse<IGiftcard[]>) => {
                this.giftcards = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.recipientService.query().subscribe(
            (res: HttpResponse<IRecipient[]>) => {
                this.recipients = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.gift.id !== undefined) {
            this.subscribeToSaveResponse(this.giftService.update(this.gift));
        } else {
            this.subscribeToSaveResponse(this.giftService.create(this.gift));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGift>>) {
        result.subscribe((res: HttpResponse<IGift>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackGiftcardById(index: number, item: IGiftcard) {
        return item.id;
    }

    trackRecipientById(index: number, item: IRecipient) {
        return item.id;
    }
}
