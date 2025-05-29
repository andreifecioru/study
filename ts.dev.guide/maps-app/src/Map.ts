interface Mappable { 
  name: string ;
  info(): string;
  location: google.maps.LatLngLiteral;
};

class Map {

  private readonly map: google.maps.Map;
  readonly mapId: string;

  constructor(containerId: string, center: google.maps.LatLngLiteral, zoom: number) {
    const container = document.getElementById(containerId);
    if (!container) {
      throw new Error(`Container with id ${containerId} not found`);
    }

    this.mapId = containerId;

    this.map = new google.maps.Map(container, {
      mapId: this.mapId,
      center: center,
      zoom: zoom,
    });
  }

  addMarker(target: Mappable): void {
    const infoWindow = new google.maps.InfoWindow({
      content: target.info(),
      ariaLabel: target.name,
    });

    const marker = new google.maps.marker.AdvancedMarkerElement({
      map: this.map,
      position: target.location,
      title: target.name,
    });

    marker.addListener('click', () => {
      infoWindow.open({ anchor: marker, map: this.map });
    });
  }

  static async initialize(): Promise<void> {
    return new Promise((resolve, reject) => {
      if ((window as any).google && (window as any).google.maps) {
        console.log('Google Maps API already loaded');
        resolve();
        return;
      }
      const script = document.createElement('script');
      script.src = `${this.GOOGLE_MAPS_BASE_URL}?key=${this.GOOGLE_MAPS_API_KEY}`;
      script.async = true;
      script.defer = true;
      script.setAttribute('async', '');
      script.setAttribute('defer', '');
      script.onload = () => {
        resolve();
        console.log('Google Maps API loaded successfully');
      };
      script.onerror = () => reject(new Error('Failed to load Google Maps API'));
      document.head.appendChild(script);
    });
  }

  static GOOGLE_MAPS_BASE_URL = 'https://maps.googleapis.com/maps/api/js';
  static GOOGLE_MAPS_API_KEY = 'AIzaSyC6YQoHRz-Lz7v72amZiKtpCfGI5ZMwvNw';
}

export { Map}
export type { Mappable };