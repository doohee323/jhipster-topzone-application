import { element, by, ElementFinder } from 'protractor';

export class StoreComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-store div table .btn-danger'));
    title = element.all(by.css('jhi-store div h2#page-heading span')).first();

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

export class StoreUpdatePage {
    pageTitle = element(by.id('jhi-store-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    countryInput = element(by.id('field_country'));
    nameInput = element(by.id('field_name'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setCountryInput(country) {
        await this.countryInput.sendKeys(country);
    }

    async getCountryInput() {
        return this.countryInput.getAttribute('value');
    }

    async setNameInput(name) {
        await this.nameInput.sendKeys(name);
    }

    async getNameInput() {
        return this.nameInput.getAttribute('value');
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

export class StoreDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-store-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-store'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
