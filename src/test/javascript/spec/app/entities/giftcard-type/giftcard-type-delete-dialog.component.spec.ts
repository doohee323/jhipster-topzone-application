/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhipsterTzApplicationTestModule } from '../../../test.module';
import { GiftcardTypeDeleteDialogComponent } from 'app/entities/giftcard-type/giftcard-type-delete-dialog.component';
import { GiftcardTypeService } from 'app/entities/giftcard-type/giftcard-type.service';

describe('Component Tests', () => {
    describe('GiftcardType Management Delete Component', () => {
        let comp: GiftcardTypeDeleteDialogComponent;
        let fixture: ComponentFixture<GiftcardTypeDeleteDialogComponent>;
        let service: GiftcardTypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTzApplicationTestModule],
                declarations: [GiftcardTypeDeleteDialogComponent]
            })
                .overrideTemplate(GiftcardTypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GiftcardTypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GiftcardTypeService);
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
