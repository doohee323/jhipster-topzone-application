/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RecipientComponentsPage, RecipientDeleteDialog, RecipientUpdatePage } from './recipient.page-object';

const expect = chai.expect;

describe('Recipient e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let recipientUpdatePage: RecipientUpdatePage;
    let recipientComponentsPage: RecipientComponentsPage;
    let recipientDeleteDialog: RecipientDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Recipients', async () => {
        await navBarPage.goToEntity('recipient');
        recipientComponentsPage = new RecipientComponentsPage();
        expect(await recipientComponentsPage.getTitle()).to.eq('jhipsterTzApplicationApp.recipient.home.title');
    });

    it('should load create Recipient page', async () => {
        await recipientComponentsPage.clickOnCreateButton();
        recipientUpdatePage = new RecipientUpdatePage();
        expect(await recipientUpdatePage.getPageTitle()).to.eq('jhipsterTzApplicationApp.recipient.home.createOrEditLabel');
        await recipientUpdatePage.cancel();
    });

    it('should create and save Recipients', async () => {
        const nbButtonsBeforeCreate = await recipientComponentsPage.countDeleteButtons();

        await recipientComponentsPage.clickOnCreateButton();
        await promise.all([
            recipientUpdatePage.setNameInput('name'),
            recipientUpdatePage.setPhoneNumberInput('phoneNumber'),
            recipientUpdatePage.setEmailInput('email'),
            recipientUpdatePage.setCreatedAtInput('2000-12-31'),
            recipientUpdatePage.setUserIdInput('5')
        ]);
        expect(await recipientUpdatePage.getNameInput()).to.eq('name');
        expect(await recipientUpdatePage.getPhoneNumberInput()).to.eq('phoneNumber');
        expect(await recipientUpdatePage.getEmailInput()).to.eq('email');
        expect(await recipientUpdatePage.getCreatedAtInput()).to.eq('2000-12-31');
        expect(await recipientUpdatePage.getUserIdInput()).to.eq('5');
        await recipientUpdatePage.save();
        expect(await recipientUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await recipientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Recipient', async () => {
        const nbButtonsBeforeDelete = await recipientComponentsPage.countDeleteButtons();
        await recipientComponentsPage.clickOnLastDeleteButton();

        recipientDeleteDialog = new RecipientDeleteDialog();
        expect(await recipientDeleteDialog.getDialogTitle()).to.eq('jhipsterTzApplicationApp.recipient.delete.question');
        await recipientDeleteDialog.clickOnConfirmButton();

        expect(await recipientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
