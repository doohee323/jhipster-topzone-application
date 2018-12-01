import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGiftcard } from 'app/shared/model/giftcard.model';
import { GiftcardService } from './giftcard.service';

@Component({
    selector: 'jhi-giftcard-delete-dialog',
    templateUrl: './giftcard-delete-dialog.component.html'
})
export class GiftcardDeleteDialogComponent {
    giftcard: IGiftcard;

    constructor(private giftcardService: GiftcardService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.giftcardService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'giftcardListModification',
                content: 'Deleted an giftcard'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-giftcard-delete-popup',
    template: ''
})
export class GiftcardDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ giftcard }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GiftcardDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.giftcard = giftcard;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
