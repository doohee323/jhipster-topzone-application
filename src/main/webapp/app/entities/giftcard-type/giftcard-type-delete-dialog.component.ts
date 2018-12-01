import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGiftcardType } from 'app/shared/model/giftcard-type.model';
import { GiftcardTypeService } from './giftcard-type.service';

@Component({
    selector: 'jhi-giftcard-type-delete-dialog',
    templateUrl: './giftcard-type-delete-dialog.component.html'
})
export class GiftcardTypeDeleteDialogComponent {
    giftcardType: IGiftcardType;

    constructor(
        private giftcardTypeService: GiftcardTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.giftcardTypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'giftcardTypeListModification',
                content: 'Deleted an giftcardType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-giftcard-type-delete-popup',
    template: ''
})
export class GiftcardTypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ giftcardType }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(GiftcardTypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.giftcardType = giftcardType;
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
