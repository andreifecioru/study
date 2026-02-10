import {  faker } from '@faker-js/faker';
import type { Mappable } from './Map';

class User implements Mappable {
  readonly name: string;
  readonly email: string;
  readonly address: string;

  readonly location: {
    lat: number;
    lng: number;
  };

  constructor() {
    this.name = faker.person.fullName();
    this.email = faker.internet.email();
    this.address = faker.location.streetAddress();
    this.location = { lat: faker.location.latitude(), lng: faker.location.longitude()};
  }

  info(): string {
    return `<div>
      <h3>${this.name}</h3>
      <p><b>Email:</b> ${this.email}</p>
      <p><b>Address:</b> ${this.address}</p>
      <p><b>Location:</b> ${this.location.lat}, ${this.location.lng}</p>
    </div>`;
  }

  showInfo(): void {
    console.log('Name: ', this.name);
    console.log('Email: ', this.email);
    console.log('Address: ', this.address);
    console.log('Location: ', this.location ? `${this.location.lat}, ${this.location.lng}` : 'Not provided');
  }
}


export { User };