import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IGiftStatusHistory } from 'app/shared/model/gift-status-history.model';
import { GiftStatusHistoryService } from './gift-status-history.service';
import { IGift } from 'app/shared/model/gift.model';
import { GiftService } from 'app/entities/gift';

@Component({
    selector: 'jhi-gift-status-history-update',
    templateUrl: './gift-status-history-update.component.html'
})
export class GiftStatusHistoryUpdateComponent implements OnInit {
    giftStatusHistory: IGiftStatusHistory;
    isSaving: boolean;

    gifts: IGift[];
    createdAtDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private giftStatusHistoryService: GiftStatusHistoryService,
        private giftService: GiftService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ giftStatusHistory }) => {
            this.giftStatusHistory = giftStatusHistory;
        });
        this.giftService.query().subscribe(
            (res: HttpResponse<IGift[]>) => {
                this.gifts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.giftStatusHistory.id !== undefined) {
            this.subscribeToSaveResponse(this.giftStatusHistoryService.update(this.giftStatusHistory));
        } else {
            this.subscribeToSaveResponse(this.giftStatusHistoryService.create(this.giftStatusHistory));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGiftStatusHistory>>) {
        result.subscribe((res: HttpResponse<IGiftStatusHistory>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackGiftById(index: number, item: IGift) {
        return item.id;
    }
}
