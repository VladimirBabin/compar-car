import axios from 'axios';
import { Car, CarFilter, PageResponse, BodyType, FuelType } from '../types/car';

const api = axios.create({
    baseURL: '/api',
    headers: {
      'Content-Type': 'application/json',
    },
  });

export const carApi = {
  // Get cars with filters and pagination
  getCars: async (filters: CarFilter = {}): Promise<PageResponse<Car>> => {
    const params = new URLSearchParams();
    
    Object.entries(filters).forEach(([key, value]) => {
      if (value !== undefined && value !== null && value !== '') {
        params.append(key, value.toString());
      }
    });
    
    const response = await api.get(`/cars?${params.toString()}`);
    return response.data;
  },

  // Get all cars (without pagination)
  getAllCars: async (): Promise<Car[]> => {
    const response = await api.get('/cars/all');
    return response.data;
  },

  // Get car by ID
  getCarById: async (id: number): Promise<Car> => {
    const response = await api.get(`/cars/${id}`);
    return response.data;
  },

  // Create new car
  createCar: async (car: Omit<Car, 'id'>): Promise<Car> => {
    const response = await api.post('/cars', car);
    return response.data;
  },

  // Update car
  updateCar: async (id: number, car: Car): Promise<Car> => {
    const response = await api.put(`/cars/${id}`, car);
    return response.data;
  },

  // Delete car
  deleteCar: async (id: number): Promise<void> => {
    await api.delete(`/cars/${id}`);
  },

  // Get body types
  getBodyTypes: async (): Promise<BodyType[]> => {
    const response = await api.get('/cars/body-types');
    return response.data;
  },

  // Get fuel types
  getFuelTypes: async (): Promise<FuelType[]> => {
    const response = await api.get('/cars/fuel-types');
    return response.data;
  },
};

export default api; 