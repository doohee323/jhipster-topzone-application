/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTzApplicationTestModule } from '../../../test.module';
import { GiftcardTypeDetailComponent } from 'app/entities/giftcard-type/giftcard-type-detail.component';
import { GiftcardType } from 'app/shared/model/giftcard-type.model';

describe('Component Tests', () => {
    describe('GiftcardType Management Detail Component', () => {
        let comp: GiftcardTypeDetailComponent;
        let fixture: ComponentFixture<GiftcardTypeDetailComponent>;
        const route = ({ data: of({ giftcardType: new GiftcardType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTzApplicationTestModule],
                declarations: [GiftcardTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GiftcardTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GiftcardTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.giftcardType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
