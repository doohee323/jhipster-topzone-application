/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterTzApplicationTestModule } from '../../../test.module';
import { GiftStatusHistoryDeleteDialogComponent } from 'app/entities/gift-status-history/gift-status-history-delete-dialog.component';
import { GiftStatusHistoryService } from 'app/entities/gift-status-history/gift-status-history.service';

describe('Component Tests', () => {
    describe('GiftStatusHistory Management Delete Component', () => {
        let comp: GiftStatusHistoryDeleteDialogComponent;
        let fixture: ComponentFixture<GiftStatusHistoryDeleteDialogComponent>;
        let service: GiftStatusHistoryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTzApplicationTestModule],
                declarations: [GiftStatusHistoryDeleteDialogComponent]
            })
                .overrideTemplate(GiftStatusHistoryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GiftStatusHistoryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GiftStatusHistoryService);
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
