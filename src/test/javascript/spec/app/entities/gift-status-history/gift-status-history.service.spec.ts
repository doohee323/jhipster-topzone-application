/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { GiftStatusHistoryService } from 'app/entities/gift-status-history/gift-status-history.service';
import { IGiftStatusHistory, GiftStatusHistory } from 'app/shared/model/gift-status-history.model';

describe('Service Tests', () => {
    describe('GiftStatusHistory Service', () => {
        let injector: TestBed;
        let service: GiftStatusHistoryService;
        let httpMock: HttpTestingController;
        let elemDefault: IGiftStatusHistory;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(GiftStatusHistoryService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new GiftStatusHistory(0, 'AAAAAAA', currentDate, 0, 0);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        createdAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a GiftStatusHistory', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        createdAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdAt: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new GiftStatusHistory(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a GiftStatusHistory', async () => {
                const returnedFromService = Object.assign(
                    {
                        giftStatus: 'BBBBBB',
                        createdAt: currentDate.format(DATE_FORMAT),
                        giftId: 1,
                        createdById: 1
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        createdAt: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of GiftStatusHistory', async () => {
                const returnedFromService = Object.assign(
                    {
                        giftStatus: 'BBBBBB',
                        createdAt: currentDate.format(DATE_FORMAT),
                        giftId: 1,
                        createdById: 1
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        createdAt: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a GiftStatusHistory', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
