import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';

import { IRecipient } from 'app/shared/model/recipient.model';
import { RecipientService } from './recipient.service';

@Component({
    selector: 'jhi-recipient-update',
    templateUrl: './recipient-update.component.html'
})
export class RecipientUpdateComponent implements OnInit {
    recipient: IRecipient;
    isSaving: boolean;
    createdAtDp: any;

    constructor(private recipientService: RecipientService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ recipient }) => {
            this.recipient = recipient;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.recipient.id !== undefined) {
            this.subscribeToSaveResponse(this.recipientService.update(this.recipient));
        } else {
            this.subscribeToSaveResponse(this.recipientService.create(this.recipient));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRecipient>>) {
        result.subscribe((res: HttpResponse<IRecipient>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
