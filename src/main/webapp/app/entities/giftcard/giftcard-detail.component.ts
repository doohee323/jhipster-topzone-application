import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGiftcard } from 'app/shared/model/giftcard.model';

@Component({
    selector: 'jhi-giftcard-detail',
    templateUrl: './giftcard-detail.component.html'
})
export class GiftcardDetailComponent implements OnInit {
    giftcard: IGiftcard;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ giftcard }) => {
            this.giftcard = giftcard;
        });
    }

    previousState() {
        window.history.back();
    }
}
