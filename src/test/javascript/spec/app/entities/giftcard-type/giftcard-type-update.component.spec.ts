/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterTzApplicationTestModule } from '../../../test.module';
import { GiftcardTypeUpdateComponent } from 'app/entities/giftcard-type/giftcard-type-update.component';
import { GiftcardTypeService } from 'app/entities/giftcard-type/giftcard-type.service';
import { GiftcardType } from 'app/shared/model/giftcard-type.model';

describe('Component Tests', () => {
    describe('GiftcardType Management Update Component', () => {
        let comp: GiftcardTypeUpdateComponent;
        let fixture: ComponentFixture<GiftcardTypeUpdateComponent>;
        let service: GiftcardTypeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTzApplicationTestModule],
                declarations: [GiftcardTypeUpdateComponent]
            })
                .overrideTemplate(GiftcardTypeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GiftcardTypeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GiftcardTypeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GiftcardType(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.giftcardType = entity;
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
                    const entity = new GiftcardType();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.giftcardType = entity;
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
