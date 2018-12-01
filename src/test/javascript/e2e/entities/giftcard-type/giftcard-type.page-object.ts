import { element, by, ElementFinder } from 'protractor';

export class GiftcardTypeComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-giftcard-type div table .btn-danger'));
    title = element.all(by.css('jhi-giftcard-type div h2#page-heading span')).first();

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

export class GiftcardTypeUpdatePage {
    pageTitle = element(by.id('jhi-giftcard-type-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    storeIdInput = element(by.id('field_storeId'));
    storeSelect = element(by.id('field_store'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setStoreIdInput(storeId) {
        await this.storeIdInput.sendKeys(storeId);
    }

    async getStoreIdInput() {
        return this.storeIdInput.getAttribute('value');
    }

    async storeSelectLastOption() {
        await this.storeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async storeSelectOption(option) {
        await this.storeSelect.sendKeys(option);
    }

    getStoreSelect(): ElementFinder {
        return this.storeSelect;
    }

    async getStoreSelectedOption() {
        return this.storeSelect.element(by.css('option:checked')).getText();
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

export class GiftcardTypeDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-giftcardType-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-giftcardType'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
