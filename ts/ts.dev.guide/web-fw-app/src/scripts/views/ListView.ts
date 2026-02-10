import { BaseModel, BaseModels, BaseProps } from "../models/Model";


abstract class ListView<T extends BaseModel<K>, K extends BaseProps>  {
    constructor(readonly parent: Element, readonly items: BaseModels<K, T>) {}

    abstract renderItem(item: T): Element

    render(): void {
        this.parent.innerHTML = '';

        this.parent.appendChild(this.createHeader());
        this.parent.appendChild(this.createItemList());
    }

    private createHeader(): DocumentFragment {
        const fragment = document.createDocumentFragment();

        const header = document.createElement('h1');
        header.textContent = 'User List';
        fragment.appendChild(header);


        return fragment;
    }

    private createItemList(): DocumentFragment {
        const fragment = document.createDocumentFragment();

        const itemList = document.createElement('ul');
        for (let item of this.items.entries) {
            itemList.appendChild(this.renderItem(item));
        }

        fragment.appendChild(itemList);

        return fragment;
    }
}

export { ListView };