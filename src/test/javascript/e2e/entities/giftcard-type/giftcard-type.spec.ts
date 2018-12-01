/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { GiftcardTypeComponentsPage, GiftcardTypeDeleteDialog, GiftcardTypeUpdatePage } from './giftcard-type.page-object';

const expect = chai.expect;

describe('GiftcardType e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let giftcardTypeUpdatePage: GiftcardTypeUpdatePage;
    let giftcardTypeComponentsPage: GiftcardTypeComponentsPage;
    let giftcardTypeDeleteDialog: GiftcardTypeDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load GiftcardTypes', async () => {
        await navBarPage.goToEntity('giftcard-type');
        giftcardTypeComponentsPage = new GiftcardTypeComponentsPage();
        expect(await giftcardTypeComponentsPage.getTitle()).to.eq('jhipsterTzApplicationApp.giftcardType.home.title');
    });

    it('should load create GiftcardType page', async () => {
        await giftcardTypeComponentsPage.clickOnCreateButton();
        giftcardTypeUpdatePage = new GiftcardTypeUpdatePage();
        expect(await giftcardTypeUpdatePage.getPageTitle()).to.eq('jhipsterTzApplicationApp.giftcardType.home.createOrEditLabel');
        await giftcardTypeUpdatePage.cancel();
    });

    it('should create and save GiftcardTypes', async () => {
        const nbButtonsBeforeCreate = await giftcardTypeComponentsPage.countDeleteButtons();

        await giftcardTypeComponentsPage.clickOnCreateButton();
        await promise.all([
            giftcardTypeUpdatePage.setNameInput('name'),
            giftcardTypeUpdatePage.setStoreIdInput('5'),
            giftcardTypeUpdatePage.storeSelectLastOption()
        ]);
        expect(await giftcardTypeUpdatePage.getNameInput()).to.eq('name');
        expect(await giftcardTypeUpdatePage.getStoreIdInput()).to.eq('5');
        await giftcardTypeUpdatePage.save();
        expect(await giftcardTypeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await giftcardTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last GiftcardType', async () => {
        const nbButtonsBeforeDelete = await giftcardTypeComponentsPage.countDeleteButtons();
        await giftcardTypeComponentsPage.clickOnLastDeleteButton();

        giftcardTypeDeleteDialog = new GiftcardTypeDeleteDialog();
        expect(await giftcardTypeDeleteDialog.getDialogTitle()).to.eq('jhipsterTzApplicationApp.giftcardType.delete.question');
        await giftcardTypeDeleteDialog.clickOnConfirmButton();

        expect(await giftcardTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
