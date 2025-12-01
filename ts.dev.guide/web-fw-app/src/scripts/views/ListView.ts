import { BaseModel, BaseProps } from "../models/Model";


abstract class ListView<T extends BaseModel<K>, K extends BaseProps>  {
    constructor(readonly parent: Element, readonly items: T[]) {}

    abstract renderItem(item: T): string

    private template(): string {
        let result = '<h1>User list</h1><ul>';
        for (let item of this.items) {
            result += this.renderItem(item);
        }

        result += '</ul>';
        return result;
    }

    render(): void {
        this.parent.innerHTML = '';

        const template = document.createElement('template');
        template.innerHTML = this.template();

        this.parent.appendChild(template.content);

    }
}

export { ListView };