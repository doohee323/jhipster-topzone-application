import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGiftcardType } from 'app/shared/model/giftcard-type.model';

@Component({
    selector: 'jhi-giftcard-type-detail',
    templateUrl: './giftcard-type-detail.component.html'
})
export class GiftcardTypeDetailComponent implements OnInit {
    giftcardType: IGiftcardType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ giftcardType }) => {
            this.giftcardType = giftcardType;
        });
    }

    previousState() {
        window.history.back();
    }
}
