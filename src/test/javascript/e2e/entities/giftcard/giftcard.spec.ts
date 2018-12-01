/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { GiftcardComponentsPage, GiftcardDeleteDialog, GiftcardUpdatePage } from './giftcard.page-object';

const expect = chai.expect;

describe('Giftcard e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let giftcardUpdatePage: GiftcardUpdatePage;
    let giftcardComponentsPage: GiftcardComponentsPage;
    let giftcardDeleteDialog: GiftcardDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Giftcards', async () => {
        await navBarPage.goToEntity('giftcard');
        giftcardComponentsPage = new GiftcardComponentsPage();
        expect(await giftcardComponentsPage.getTitle()).to.eq('jhipsterTzApplicationApp.giftcard.home.title');
    });

    it('should load create Giftcard page', async () => {
        await giftcardComponentsPage.clickOnCreateButton();
        giftcardUpdatePage = new GiftcardUpdatePage();
        expect(await giftcardUpdatePage.getPageTitle()).to.eq('jhipsterTzApplicationApp.giftcard.home.createOrEditLabel');
        await giftcardUpdatePage.cancel();
    });

    it('should create and save Giftcards', async () => {
        const nbButtonsBeforeCreate = await giftcardComponentsPage.countDeleteButtons();

        await giftcardComponentsPage.clickOnCreateButton();
        await promise.all([
            giftcardUpdatePage.setNameInput('name'),
            giftcardUpdatePage.setAmountInput('5'),
            giftcardUpdatePage.setUnitInput('unit'),
            giftcardUpdatePage.setGiftcardTypeIdInput('5'),
            giftcardUpdatePage.giftcardTypeSelectLastOption()
        ]);
        expect(await giftcardUpdatePage.getNameInput()).to.eq('name');
        expect(await giftcardUpdatePage.getAmountInput()).to.eq('5');
        expect(await giftcardUpdatePage.getUnitInput()).to.eq('unit');
        expect(await giftcardUpdatePage.getGiftcardTypeIdInput()).to.eq('5');
        await giftcardUpdatePage.save();
        expect(await giftcardUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await giftcardComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Giftcard', async () => {
        const nbButtonsBeforeDelete = await giftcardComponentsPage.countDeleteButtons();
        await giftcardComponentsPage.clickOnLastDeleteButton();

        giftcardDeleteDialog = new GiftcardDeleteDialog();
        expect(await giftcardDeleteDialog.getDialogTitle()).to.eq('jhipsterTzApplicationApp.giftcard.delete.question');
        await giftcardDeleteDialog.clickOnConfirmButton();

        expect(await giftcardComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
