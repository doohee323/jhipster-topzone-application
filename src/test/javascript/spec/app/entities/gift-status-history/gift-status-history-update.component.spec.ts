/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterTzApplicationTestModule } from '../../../test.module';
import { GiftStatusHistoryUpdateComponent } from 'app/entities/gift-status-history/gift-status-history-update.component';
import { GiftStatusHistoryService } from 'app/entities/gift-status-history/gift-status-history.service';
import { GiftStatusHistory } from 'app/shared/model/gift-status-history.model';

describe('Component Tests', () => {
    describe('GiftStatusHistory Management Update Component', () => {
        let comp: GiftStatusHistoryUpdateComponent;
        let fixture: ComponentFixture<GiftStatusHistoryUpdateComponent>;
        let service: GiftStatusHistoryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTzApplicationTestModule],
                declarations: [GiftStatusHistoryUpdateComponent]
            })
                .overrideTemplate(GiftStatusHistoryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GiftStatusHistoryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GiftStatusHistoryService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GiftStatusHistory(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.giftStatusHistory = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GiftStatusHistory();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.giftStatusHistory = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
