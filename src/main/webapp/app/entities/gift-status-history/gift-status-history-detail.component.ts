import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGiftStatusHistory } from 'app/shared/model/gift-status-history.model';

@Component({
    selector: 'jhi-gift-status-history-detail',
    templateUrl: './gift-status-history-detail.component.html'
})
export class GiftStatusHistoryDetailComponent implements OnInit {
    giftStatusHistory: IGiftStatusHistory;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ giftStatusHistory }) => {
            this.giftStatusHistory = giftStatusHistory;
        });
    }

    previousState() {
        window.history.back();
    }
}
