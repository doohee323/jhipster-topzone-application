/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterTzApplicationTestModule } from '../../../test.module';
import { GiftcardDeleteDialogComponent } from 'app/entities/giftcard/giftcard-delete-dialog.component';
import { GiftcardService } from 'app/entities/giftcard/giftcard.service';

describe('Component Tests', () => {
    describe('Giftcard Management Delete Component', () => {
        let comp: GiftcardDeleteDialogComponent;
        let fixture: ComponentFixture<GiftcardDeleteDialogComponent>;
        let service: GiftcardService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTzApplicationTestModule],
                declarations: [GiftcardDeleteDialogComponent]
            })
                .overrideTemplate(GiftcardDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GiftcardDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GiftcardService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
