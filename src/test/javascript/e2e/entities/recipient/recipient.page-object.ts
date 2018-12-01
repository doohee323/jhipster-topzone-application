import { element, by, ElementFinder } from 'protractor';

export class RecipientComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-recipient div table .btn-danger'));
    title = element.all(by.css('jhi-recipient div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class RecipientUpdatePage {
    pageTitle = element(by.id('jhi-recipient-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    phoneNumberInput = element(by.id('field_phoneNumber'));
    emailInput = element(by.id('field_email'));
    createdAtInput = element(by.id('field_createdAt'));
    userIdInput = element(by.id('field_userId'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setPhoneNumberInput(phoneNumber) {
        await this.phoneNumberInput.sendKeys(phoneNumber);
    }

    async getPhoneNumberInput() {
        return this.phoneNumberInput.getAttribute('value');
    }

    async setEmailInput(email) {
        await this.emailInput.sendKeys(email);
    }

    async getEmailInput() {
        return this.emailInput.getAttribute('value');
    }

    async setCreatedAtInput(createdAt) {
        await this.createdAtInput.sendKeys(createdAt);
    }

    async getCreatedAtInput() {
        return this.createdAtInput.getAttribute('value');
    }

    async setUserIdInput(userId) {
        await this.userIdInput.sendKeys(userId);
    }

    async getUserIdInput() {
        return this.userIdInput.getAttribute('value');
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class RecipientDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-recipient-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-recipient'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
