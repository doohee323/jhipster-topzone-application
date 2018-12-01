/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { GiftComponentsPage, GiftDeleteDialog, GiftUpdatePage } from './gift.page-object';

const expect = chai.expect;

describe('Gift e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let giftUpdatePage: GiftUpdatePage;
    let giftComponentsPage: GiftComponentsPage;
    let giftDeleteDialog: GiftDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Gifts', async () => {
        await navBarPage.goToEntity('gift');
        giftComponentsPage = new GiftComponentsPage();
        expect(await giftComponentsPage.getTitle()).to.eq('jhipsterTzApplicationApp.gift.home.title');
    });

    it('should load create Gift page', async () => {
        await giftComponentsPage.clickOnCreateButton();
        giftUpdatePage = new GiftUpdatePage();
        expect(await giftUpdatePage.getPageTitle()).to.eq('jhipsterTzApplicationApp.gift.home.createOrEditLabel');
        await giftUpdatePage.cancel();
    });

    it('should create and save Gifts', async () => {
        const nbButtonsBeforeCreate = await giftComponentsPage.countDeleteButtons();

        await giftComponentsPage.clickOnCreateButton();
        await promise.all([
            giftUpdatePage.setAmountInput('5'),
            giftUpdatePage.setOrderedAtInput('2000-12-31'),
            giftUpdatePage.setFeeInput('5'),
            giftUpdatePage.setExchangeRateInput('5'),
            giftUpdatePage.setTotalAmountInput('5'),
            giftUpdatePage.setSendFromInput('sendFrom'),
            giftUpdatePage.setMessageInput('message'),
            giftUpdatePage.setReferenceNumberInput('referenceNumber'),
            giftUpdatePage.setDepositorInput('depositor'),
            giftUpdatePage.setDisplayAtInput('displayAt'),
            giftUpdatePage.setSenderIdInput('5'),
            giftUpdatePage.setRecipientIdInput('5'),
            giftUpdatePage.setGiftcardIdInput('5'),
            giftUpdatePage.giftcardSelectLastOption(),
            giftUpdatePage.recipientSelectLastOption()
        ]);
        expect(await giftUpdatePage.getAmountInput()).to.eq('5');
        expect(await giftUpdatePage.getOrderedAtInput()).to.eq('2000-12-31');
        expect(await giftUpdatePage.getFeeInput()).to.eq('5');
        expect(await giftUpdatePage.getExchangeRateInput()).to.eq('5');
        expect(await giftUpdatePage.getTotalAmountInput()).to.eq('5');
        expect(await giftUpdatePage.getSendFromInput()).to.eq('sendFrom');
        expect(await giftUpdatePage.getMessageInput()).to.eq('message');
        expect(await giftUpdatePage.getReferenceNumberInput()).to.eq('referenceNumber');
        expect(await giftUpdatePage.getDepositorInput()).to.eq('depositor');
        expect(await giftUpdatePage.getDisplayAtInput()).to.eq('displayAt');
        expect(await giftUpdatePage.getSenderIdInput()).to.eq('5');
        expect(await giftUpdatePage.getRecipientIdInput()).to.eq('5');
        expect(await giftUpdatePage.getGiftcardIdInput()).to.eq('5');
        await giftUpdatePage.save();
        expect(await giftUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await giftComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Gift', async () => {
        const nbButtonsBeforeDelete = await giftComponentsPage.countDeleteButtons();
        await giftComponentsPage.clickOnLastDeleteButton();

        giftDeleteDialog = new GiftDeleteDialog();
        expect(await giftDeleteDialog.getDialogTitle()).to.eq('jhipsterTzApplicationApp.gift.delete.question');
        await giftDeleteDialog.clickOnConfirmButton();

        expect(await giftComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
