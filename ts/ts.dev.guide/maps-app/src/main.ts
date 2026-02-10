import { User } from "./User";
import { Company } from "./Company";
import { Map } from "./Map";

async function initialize(): Promise<void> {
  await Map.initialize()
  await google.maps.importLibrary('marker');

  main();
}

function main(): void {
  const user = new User();
  user.showInfo();

  const company = new Company();
  company.showInfo();

  const map = new Map("map-container", { lat: 0.0, lng: 0.0 }, 3);
  map.addMarker(user);
  map.addMarker(company);
}

initialize();
