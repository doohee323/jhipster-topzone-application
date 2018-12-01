import { element, by, ElementFinder } from 'protractor';

export class GiftStatusHistoryComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-gift-status-history div table .btn-danger'));
    title = element.all(by.css('jhi-gift-status-history div h2#page-heading span')).first();

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

export class GiftStatusHistoryUpdatePage {
    pageTitle = element(by.id('jhi-gift-status-history-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    giftStatusInput = element(by.id('field_giftStatus'));
    createdAtInput = element(by.id('field_createdAt'));
    giftIdInput = element(by.id('field_giftId'));
    createdByIdInput = element(by.id('field_createdById'));
    giftSelect = element(by.id('field_gift'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setGiftStatusInput(giftStatus) {
        await this.giftStatusInput.sendKeys(giftStatus);
    }

    async getGiftStatusInput() {
        return this.giftStatusInput.getAttribute('value');
    }

    async setCreatedAtInput(createdAt) {
        await this.createdAtInput.sendKeys(createdAt);
    }

    async getCreatedAtInput() {
        return this.createdAtInput.getAttribute('value');
    }

    async setGiftIdInput(giftId) {
        await this.giftIdInput.sendKeys(giftId);
    }

    async getGiftIdInput() {
        return this.giftIdInput.getAttribute('value');
    }

    async setCreatedByIdInput(createdById) {
        await this.createdByIdInput.sendKeys(createdById);
    }

    async getCreatedByIdInput() {
        return this.createdByIdInput.getAttribute('value');
    }

    async giftSelectLastOption() {
        await this.giftSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async giftSelectOption(option) {
        await this.giftSelect.sendKeys(option);
    }

    getGiftSelect(): ElementFinder {
        return this.giftSelect;
    }

    async getGiftSelectedOption() {
        return this.giftSelect.element(by.css('option:checked')).getText();
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

export class GiftStatusHistoryDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-giftStatusHistory-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-giftStatusHistory'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
