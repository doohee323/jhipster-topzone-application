/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTzApplicationTestModule } from '../../../test.module';
import { GiftcardDetailComponent } from 'app/entities/giftcard/giftcard-detail.component';
import { Giftcard } from 'app/shared/model/giftcard.model';

describe('Component Tests', () => {
    describe('Giftcard Management Detail Component', () => {
        let comp: GiftcardDetailComponent;
        let fixture: ComponentFixture<GiftcardDetailComponent>;
        const route = ({ data: of({ giftcard: new Giftcard(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTzApplicationTestModule],
                declarations: [GiftcardDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GiftcardDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GiftcardDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.giftcard).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
