import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGiftStatusHistory } from 'app/shared/model/gift-status-history.model';
import { GiftStatusHistoryService } from './gift-status-history.service';

@Component({
    selector: 'jhi-gift-status-history-delete-dialog',
    templateUrl: './gift-status-history-delete-dialog.component.html'
})
export class GiftStatusHistoryDeleteDialogComponent {
    giftStatusHistory: IGiftStatusHistory;

    constructor(
        private giftStatusHistoryService: GiftStatusHistoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.giftStatusHistoryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'giftStatusHistoryListModification',
                content: 'Deleted an giftStatusHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-gift-status-history-delete-popup',
    template: ''
})
export class GiftStatusHistoryDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ giftStatusHistory }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GiftStatusHistoryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.giftStatusHistory = giftStatusHistory;
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
