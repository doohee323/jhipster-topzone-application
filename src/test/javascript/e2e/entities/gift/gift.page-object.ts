import { element, by, ElementFinder } from 'protractor';

export class GiftComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-gift div table .btn-danger'));
    title = element.all(by.css('jhi-gift div h2#page-heading span')).first();

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

export class GiftUpdatePage {
    pageTitle = element(by.id('jhi-gift-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    amountInput = element(by.id('field_amount'));
    orderedAtInput = element(by.id('field_orderedAt'));
    feeInput = element(by.id('field_fee'));
    exchangeRateInput = element(by.id('field_exchangeRate'));
    totalAmountInput = element(by.id('field_totalAmount'));
    sendFromInput = element(by.id('field_sendFrom'));
    messageInput = element(by.id('field_message'));
    referenceNumberInput = element(by.id('field_referenceNumber'));
    depositorInput = element(by.id('field_depositor'));
    displayAtInput = element(by.id('field_displayAt'));
    senderIdInput = element(by.id('field_senderId'));
    recipientIdInput = element(by.id('field_recipientId'));
    giftcardIdInput = element(by.id('field_giftcardId'));
    giftcardSelect = element(by.id('field_giftcard'));
    recipientSelect = element(by.id('field_recipient'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setAmountInput(amount) {
        await this.amountInput.sendKeys(amount);
    }

    async getAmountInput() {
        return this.amountInput.getAttribute('value');
    }

    async setOrderedAtInput(orderedAt) {
        await this.orderedAtInput.sendKeys(orderedAt);
    }

    async getOrderedAtInput() {
        return this.orderedAtInput.getAttribute('value');
    }

    async setFeeInput(fee) {
        await this.feeInput.sendKeys(fee);
    }

    async getFeeInput() {
        return this.feeInput.getAttribute('value');
    }

    async setExchangeRateInput(exchangeRate) {
        await this.exchangeRateInput.sendKeys(exchangeRate);
    }

    async getExchangeRateInput() {
        return this.exchangeRateInput.getAttribute('value');
    }

    async setTotalAmountInput(totalAmount) {
        await this.totalAmountInput.sendKeys(totalAmount);
    }

    async getTotalAmountInput() {
        return this.totalAmountInput.getAttribute('value');
    }

    async setSendFromInput(sendFrom) {
        await this.sendFromInput.sendKeys(sendFrom);
    }

    async getSendFromInput() {
        return this.sendFromInput.getAttribute('value');
    }

    async setMessageInput(message) {
        await this.messageInput.sendKeys(message);
    }

    async getMessageInput() {
        return this.messageInput.getAttribute('value');
    }

    async setReferenceNumberInput(referenceNumber) {
        await this.referenceNumberInput.sendKeys(referenceNumber);
    }

    async getReferenceNumberInput() {
        return this.referenceNumberInput.getAttribute('value');
    }

    async setDepositorInput(depositor) {
        await this.depositorInput.sendKeys(depositor);
    }

    async getDepositorInput() {
        return this.depositorInput.getAttribute('value');
    }

    async setDisplayAtInput(displayAt) {
        await this.displayAtInput.sendKeys(displayAt);
    }

    async getDisplayAtInput() {
        return this.displayAtInput.getAttribute('value');
    }

    async setSenderIdInput(senderId) {
        await this.senderIdInput.sendKeys(senderId);
    }

    async getSenderIdInput() {
        return this.senderIdInput.getAttribute('value');
    }

    async setRecipientIdInput(recipientId) {
        await this.recipientIdInput.sendKeys(recipientId);
    }

    async getRecipientIdInput() {
        return this.recipientIdInput.getAttribute('value');
    }

    async setGiftcardIdInput(giftcardId) {
        await this.giftcardIdInput.sendKeys(giftcardId);
    }

    async getGiftcardIdInput() {
        return this.giftcardIdInput.getAttribute('value');
    }

    async giftcardSelectLastOption() {
        await this.giftcardSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async giftcardSelectOption(option) {
        await this.giftcardSelect.sendKeys(option);
    }

    getGiftcardSelect(): ElementFinder {
        return this.giftcardSelect;
    }

    async getGiftcardSelectedOption() {
        return this.giftcardSelect.element(by.css('option:checked')).getText();
    }

    async recipientSelectLastOption() {
        await this.recipientSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    async recipientSelectOption(option) {
        await this.recipientSelect.sendKeys(option);
    }

    getRecipientSelect(): ElementFinder {
        return this.recipientSelect;
    }

    async getRecipientSelectedOption() {
        return this.recipientSelect.element(by.css('option:checked')).getText();
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

export class GiftDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-gift-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-gift'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
