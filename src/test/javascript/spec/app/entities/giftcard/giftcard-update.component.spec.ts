/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterTzApplicationTestModule } from '../../../test.module';
import { GiftcardUpdateComponent } from 'app/entities/giftcard/giftcard-update.component';
import { GiftcardService } from 'app/entities/giftcard/giftcard.service';
import { Giftcard } from 'app/shared/model/giftcard.model';

describe('Component Tests', () => {
    describe('Giftcard Management Update Component', () => {
        let comp: GiftcardUpdateComponent;
        let fixture: ComponentFixture<GiftcardUpdateComponent>;
        let service: GiftcardService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTzApplicationTestModule],
                declarations: [GiftcardUpdateComponent]
            })
                .overrideTemplate(GiftcardUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GiftcardUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GiftcardService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Giftcard(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.giftcard = entity;
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
                    const entity = new Giftcard();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.giftcard = entity;
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
