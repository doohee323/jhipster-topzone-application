/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterTzApplicationTestModule } from '../../../test.module';
import { GiftStatusHistoryDetailComponent } from 'app/entities/gift-status-history/gift-status-history-detail.component';
import { GiftStatusHistory } from 'app/shared/model/gift-status-history.model';

describe('Component Tests', () => {
    describe('GiftStatusHistory Management Detail Component', () => {
        let comp: GiftStatusHistoryDetailComponent;
        let fixture: ComponentFixture<GiftStatusHistoryDetailComponent>;
        const route = ({ data: of({ giftStatusHistory: new GiftStatusHistory(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterTzApplicationTestModule],
                declarations: [GiftStatusHistoryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GiftStatusHistoryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GiftStatusHistoryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.giftStatusHistory).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
