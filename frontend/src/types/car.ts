export interface Car {
  id?: number;
  model: string;
  manufacturingYear: number;
  engineVolume: number;
  bodyType: BodyType;
  fuelType: FuelType;
  trunkSize: number;
  fuelConsumption: number;
  averageServicePrice: number;
  price: number;
  mileage: number;
}

export enum BodyType {
  SEDAN = 'SEDAN',
  HATCHBACK = 'HATCHBACK',
  STATION_WAGON = 'STATION_WAGON',
  CROSSOVER = 'CROSSOVER',
  SUV = 'SUV',
  COUPE = 'COUPE',
  CONVERTIBLE = 'CONVERTIBLE',
  MINIVAN = 'MINIVAN',
  PICKUP = 'PICKUP',
  VAN = 'VAN',
  WAGON = 'WAGON',
  LIFTBACK = 'LIFTBACK',
  FASTBACK = 'FASTBACK',
  SHOOTING_BRAKE = 'SHOOTING_BRAKE',
  MICROCAR = 'MICROCAR',
  LIMOUSINE = 'LIMOUSINE',
  ROADSTER = 'ROADSTER',
  TARGA = 'TARGA',
  HARDTOP = 'HARDTOP',
  OTHER = 'OTHER'
}

export enum FuelType {
  GASOLINE = 'GASOLINE',
  DIESEL = 'DIESEL',
  HYBRID = 'HYBRID',
  ELECTRIC = 'ELECTRIC',
  PLUG_IN_HYBRID = 'PLUG_IN_HYBRID',
  HYDROGEN = 'HYDROGEN',
  LPG = 'LPG',
  CNG = 'CNG',
  ETHANOL = 'ETHANOL',
  BIODIESEL = 'BIODIESEL',
  OTHER = 'OTHER'
}

export interface CarFilter {
  model?: string;
  manufacturingYearFrom?: number;
  manufacturingYearTo?: number;
  engineVolumeFrom?: number;
  engineVolumeTo?: number;
  bodyType?: BodyType;
  fuelType?: FuelType;
  trunkSizeFrom?: number;
  trunkSizeTo?: number;
  fuelConsumptionFrom?: number;
  fuelConsumptionTo?: number;
  averageServicePriceFrom?: number;
  averageServicePriceTo?: number;
  priceFrom?: number;
  priceTo?: number;
  mileageFrom?: number;
  mileageTo?: number;
  page?: number;
  size?: number;
  sortBy?: string;
  sortDirection?: 'ASC' | 'DESC';
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
} 