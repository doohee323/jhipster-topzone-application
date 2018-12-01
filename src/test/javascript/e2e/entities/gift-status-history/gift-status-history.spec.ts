/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
    GiftStatusHistoryComponentsPage,
    GiftStatusHistoryDeleteDialog,
    GiftStatusHistoryUpdatePage
} from './gift-status-history.page-object';

const expect = chai.expect;

describe('GiftStatusHistory e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let giftStatusHistoryUpdatePage: GiftStatusHistoryUpdatePage;
    let giftStatusHistoryComponentsPage: GiftStatusHistoryComponentsPage;
    let giftStatusHistoryDeleteDialog: GiftStatusHistoryDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load GiftStatusHistories', async () => {
        await navBarPage.goToEntity('gift-status-history');
        giftStatusHistoryComponentsPage = new GiftStatusHistoryComponentsPage();
        expect(await giftStatusHistoryComponentsPage.getTitle()).to.eq('jhipsterTzApplicationApp.giftStatusHistory.home.title');
    });

    it('should load create GiftStatusHistory page', async () => {
        await giftStatusHistoryComponentsPage.clickOnCreateButton();
        giftStatusHistoryUpdatePage = new GiftStatusHistoryUpdatePage();
        expect(await giftStatusHistoryUpdatePage.getPageTitle()).to.eq('jhipsterTzApplicationApp.giftStatusHistory.home.createOrEditLabel');
        await giftStatusHistoryUpdatePage.cancel();
    });

    it('should create and save GiftStatusHistories', async () => {
        const nbButtonsBeforeCreate = await giftStatusHistoryComponentsPage.countDeleteButtons();

        await giftStatusHistoryComponentsPage.clickOnCreateButton();
        await promise.all([
            giftStatusHistoryUpdatePage.setGiftStatusInput('giftStatus'),
            giftStatusHistoryUpdatePage.setCreatedAtInput('2000-12-31'),
            giftStatusHistoryUpdatePage.setGiftIdInput('5'),
            giftStatusHistoryUpdatePage.setCreatedByIdInput('5'),
            giftStatusHistoryUpdatePage.giftSelectLastOption()
        ]);
        expect(await giftStatusHistoryUpdatePage.getGiftStatusInput()).to.eq('giftStatus');
        expect(await giftStatusHistoryUpdatePage.getCreatedAtInput()).to.eq('2000-12-31');
        expect(await giftStatusHistoryUpdatePage.getGiftIdInput()).to.eq('5');
        expect(await giftStatusHistoryUpdatePage.getCreatedByIdInput()).to.eq('5');
        await giftStatusHistoryUpdatePage.save();
        expect(await giftStatusHistoryUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await giftStatusHistoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last GiftStatusHistory', async () => {
        const nbButtonsBeforeDelete = await giftStatusHistoryComponentsPage.countDeleteButtons();
        await giftStatusHistoryComponentsPage.clickOnLastDeleteButton();

        giftStatusHistoryDeleteDialog = new GiftStatusHistoryDeleteDialog();
        expect(await giftStatusHistoryDeleteDialog.getDialogTitle()).to.eq('jhipsterTzApplicationApp.giftStatusHistory.delete.question');
        await giftStatusHistoryDeleteDialog.clickOnConfirmButton();

        expect(await giftStatusHistoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
