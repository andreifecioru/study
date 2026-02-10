import { BaseModel, BaseProps } from "../models/Model";


abstract class View<Props extends BaseProps> {
    constructor(readonly parent: Element, readonly model: BaseModel<Props>) {
        this.model.on('change', () => {
            this.render();
        });
    }

    private bindEvents(element: DocumentFragment): void {
        const eventsMap = this.eventsMap();

        for (let eventKey in eventsMap) {
            const [eventName, selector] = eventKey.split(':');
            const callback = eventsMap[eventKey];

            element.querySelectorAll(selector).forEach(element => {
                element.addEventListener(eventName, callback);
            });
        }
    }

    protected regionMap(): { [key: string]: string } {
        return {};
    }

    protected regions: { [key: string]: Element } = {};

    protected eventsMap(): { [key: string]: () => void } {
        return {};
    }

    protected abstract template(): string

    protected onRender(): void {}

    render(): void {
        this.parent.innerHTML = '';

        const template = document.createElement('template');
        template.innerHTML = this.template();
        this.bindEvents(template.content);
        this.mapRegions(template.content);

        this.onRender();

        this.parent.appendChild(template.content);
    }

    private mapRegions(fragment: DocumentFragment): void {
        const regionMap = this.regionMap();
        for (let regionKey in regionMap) {
            const element = fragment.querySelector(regionMap[regionKey]);
            if (element) {
                this.regions[regionKey] = element;
            }
        }
    }
}

export { View };