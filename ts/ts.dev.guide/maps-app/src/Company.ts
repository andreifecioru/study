import { faker } from "@faker-js/faker";
import type { Mappable } from "./Map";

class Company implements Mappable {
  readonly name: string;
  readonly catchPhrase: string;

  readonly location: {
    lat: number;
    lng: number;
  };

  constructor() {
    this.name = faker.company.name();
    this.catchPhrase = faker.company.catchPhrase();
    this.location = {
      lat: faker.location.latitude(),
      lng: faker.location.longitude(),
    };
  }

  info(): string {
    return `<div>
      <h3>${this.name}</h3>
      <p>${this.catchPhrase}</p>
      <p><b>Location:</b> ${this.location.lat}, ${this.location.lng}</p>
    </div>`;
  }

  showInfo(): void {
    console.log("Company Name: ", this.name);
    console.log("Catch Phrase: ", this.catchPhrase);
    console.log("Location: ", `${this.location.lat}, ${this.location.lng}`);
  }
}

export { Company };
