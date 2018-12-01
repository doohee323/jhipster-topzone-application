import { element, by, ElementFinder } from 'protractor';

export class GiftcardComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-giftcard div table .btn-danger'));
    title = element.all(by.css('jhi-giftcard div h2#page-heading span')).first();

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

export class GiftcardUpdatePage {
    pageTitle = element(by.id('jhi-giftcard-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nameInput = element(by.id('field_name'));
    amountInput = element(by.id('field_amount'));
    unitInput = element(by.id('field_unit'));
    giftcardTypeIdInput = element(by.id('field_giftcardTypeId'));
    giftcardTypeSelect = element(by.id('field_giftcardType'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    async setAmountInput(amount) {
        await this.amountInput.sendKeys(amount);
    }

    async getAmountInput() {
        return this.amountInput.getAttribute('value');
    }

    async setUnitInput(unit) {
        await this.unitInput.sendKeys(unit);
    }

    async getUnitInput() {
        return this.unitInput.getAttribute('value');
    }

    async setGiftcardTypeIdInput(giftcardTypeId) {
        await this.giftcardTypeIdInput.sendKeys(giftcardTypeId);
    }

    async getGiftcardTypeIdInput() {
        return this.giftcardTypeIdInput.getAttribute('value');
    }

    async giftcardTypeSelectLastOption() {
        await this.giftcardTypeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async giftcardTypeSelectOption(option) {
        await this.giftcardTypeSelect.sendKeys(option);
    }

    getGiftcardTypeSelect(): ElementFinder {
        return this.giftcardTypeSelect;
    }

    async getGiftcardTypeSelectedOption() {
        return this.giftcardTypeSelect.element(by.css('option:checked')).getText();
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

export class GiftcardDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-giftcard-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-giftcard'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
