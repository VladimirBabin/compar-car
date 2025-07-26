import { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { useForm } from 'react-hook-form';
import { ArrowLeft, Save } from 'lucide-react';
import { carApi } from '../services/api';
import { Car } from '../types/car';
import toast from 'react-hot-toast';

const CarForm = () => {
  const navigate = useNavigate();
  const { id } = useParams();
  const queryClient = useQueryClient();
  const isEditing = Boolean(id);

  const [selectedYear, setSelectedYear] = useState<number>(new Date().getFullYear());

  const { data: bodyTypes } = useQuery({
    queryKey: ['bodyTypes'],
    queryFn: () => carApi.getBodyTypes(),
  });
  
  const { data: fuelTypes } = useQuery({
    queryKey: ['fuelTypes'],
    queryFn: () => carApi.getFuelTypes(),
  });

  const { data: car, isLoading: isLoadingCar } = useQuery({
    queryKey: ['car', id],
    queryFn: () => carApi.getCarById(Number(id)),
    enabled: isEditing,
  });

  const createMutation = useMutation({
    mutationFn: (data: Omit<Car, 'id'>) => carApi.createCar(data),
    onSuccess: () => {
      toast.success('Car created successfully!');
      queryClient.invalidateQueries({ queryKey: ['cars'] });
      navigate('/');
    },
    onError: () => {
      toast.error('Failed to create car');
    },
  });

  const updateMutation = useMutation({
    mutationFn: (data: Car) => carApi.updateCar(Number(id), data),
    onSuccess: () => {
      toast.success('Car updated successfully!');
      queryClient.invalidateQueries({ queryKey: ['cars'] });
      navigate('/');
    },
    onError: () => {
      toast.error('Failed to update car');
    },
  });

  const {
    register,
    handleSubmit,
    setValue,
    formState: { errors },
  } = useForm<Car>();

  useEffect(() => {
    if (car) {
      setValue('model', car.model);
      setValue('manufacturingYear', car.manufacturingYear);
      setValue('engineVolume', car.engineVolume);
      setValue('bodyType', car.bodyType);
      setValue('fuelType', car.fuelType);
      setValue('trunkSize', car.trunkSize);
      setValue('fuelConsumption', car.fuelConsumption);
      setValue('averageServicePrice', car.averageServicePrice);
      setValue('price', car.price);
      setValue('mileage', car.mileage);
      setSelectedYear(car.manufacturingYear);
    }
  }, [car, setValue]);

  const onSubmit = (data: Car) => {
    if (isEditing) {
      updateMutation.mutate({ ...data, id: Number(id) });
    } else {
      createMutation.mutate(data);
    }
  };

  const generateYearOptions = () => {
    const currentYear = new Date().getFullYear();
    const years = [];
    for (let year = currentYear; year >= 1900; year--) {
      years.push(year);
    }
    return years;
  };

  if (isEditing && isLoadingCar) {
    return (
      <div className="text-center py-8">
        <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-primary-600 mx-auto"></div>
        <p className="mt-2 text-gray-600">Loading car data...</p>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto">
      <div className="flex items-center space-x-4 mb-6">
        <button
          onClick={() => navigate('/')}
          className="btn-secondary flex items-center space-x-2"
        >
          <ArrowLeft className="h-4 w-4" />
          <span>Back</span>
        </button>
        <h1 className="text-3xl font-bold text-gray-900">
          {isEditing ? 'Edit Car' : 'Add New Car'}
        </h1>
      </div>

      <form onSubmit={handleSubmit(onSubmit)} className="bg-white rounded-lg shadow-md p-6">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {/* Model */}
          <div className="md:col-span-2">
            <label className="form-label">Car Model *</label>
            <input
              type="text"
              {...register('model', { required: 'Car model is required' })}
              className={`input-field ${errors.model ? 'border-red-500' : ''}`}
              placeholder="e.g., BMW X5, Audi A4"
            />
            {errors.model && (
              <p className="mt-1 text-sm text-red-600">{errors.model.message}</p>
            )}
          </div>

          {/* Manufacturing Year */}
          <div>
            <label className="form-label">Manufacturing Year *</label>
            <select
              {...register('manufacturingYear', { required: 'Manufacturing year is required' })}
              className={`input-field ${errors.manufacturingYear ? 'border-red-500' : ''}`}
              value={selectedYear}
              onChange={(e) => setSelectedYear(Number(e.target.value))}
            >
              <option value="">Select Year</option>
              {generateYearOptions().map((year) => (
                <option key={year} value={year}>
                  {year}
                </option>
              ))}
            </select>
            {errors.manufacturingYear && (
              <p className="mt-1 text-sm text-red-600">{errors.manufacturingYear.message}</p>
            )}
          </div>

          {/* Engine Volume */}
          <div>
            <label className="form-label">Engine Volume (L) *</label>
            <input
              type="number"
              step="0.1"
              {...register('engineVolume', {
                required: 'Engine volume is required',
                min: { value: 0.5, message: 'Engine volume must be at least 0.5L' },
                max: { value: 10.0, message: 'Engine volume cannot exceed 10.0L' },
              })}
              className={`input-field ${errors.engineVolume ? 'border-red-500' : ''}`}
              placeholder="e.g., 2.0"
            />
            {errors.engineVolume && (
              <p className="mt-1 text-sm text-red-600">{errors.engineVolume.message}</p>
            )}
          </div>

          {/* Body Type */}
          <div>
            <label className="form-label">Body Type *</label>
            <select
              {...register('bodyType', { required: 'Body type is required' })}
              className={`input-field ${errors.bodyType ? 'border-red-500' : ''}`}
            >
              <option value="">Select Body Type</option>
              {bodyTypes?.map((type) => (
                <option key={type} value={type}>
                  {type.replace(/_/g, ' ')}
                </option>
              ))}
            </select>
            {errors.bodyType && (
              <p className="mt-1 text-sm text-red-600">{errors.bodyType.message}</p>
            )}
          </div>

          {/* Fuel Type */}
          <div>
            <label className="form-label">Fuel Type *</label>
            <select
              {...register('fuelType', { required: 'Fuel type is required' })}
              className={`input-field ${errors.fuelType ? 'border-red-500' : ''}`}
            >
              <option value="">Select Fuel Type</option>
              {fuelTypes?.map((type) => (
                <option key={type} value={type}>
                  {type.replace(/_/g, ' ')}
                </option>
              ))}
            </select>
            {errors.fuelType && (
              <p className="mt-1 text-sm text-red-600">{errors.fuelType.message}</p>
            )}
          </div>

          {/* Trunk Size */}
          <div>
            <label className="form-label">Trunk Size (L) *</label>
            <input
              type="number"
              {...register('trunkSize', {
                required: 'Trunk size is required',
                min: { value: 100, message: 'Trunk size must be at least 100L' },
                max: { value: 3000, message: 'Trunk size cannot exceed 3000L' },
              })}
              className={`input-field ${errors.trunkSize ? 'border-red-500' : ''}`}
              placeholder="e.g., 500"
            />
            {errors.trunkSize && (
              <p className="mt-1 text-sm text-red-600">{errors.trunkSize.message}</p>
            )}
          </div>

          {/* Fuel Consumption */}
          <div>
            <label className="form-label">Fuel Consumption (L/100km) *</label>
            <input
              type="number"
              step="0.1"
              {...register('fuelConsumption', {
                required: 'Fuel consumption is required',
                min: { value: 1.0, message: 'Fuel consumption must be at least 1.0L/100km' },
                max: { value: 30.0, message: 'Fuel consumption cannot exceed 30.0L/100km' },
              })}
              className={`input-field ${errors.fuelConsumption ? 'border-red-500' : ''}`}
              placeholder="e.g., 6.5"
            />
            {errors.fuelConsumption && (
              <p className="mt-1 text-sm text-red-600">{errors.fuelConsumption.message}</p>
            )}
          </div>

          {/* Average Service Price */}
          <div>
            <label className="form-label">Average Service Price (EUR) *</label>
            <input
              type="number"
              step="0.01"
              {...register('averageServicePrice', {
                required: 'Average service price is required',
                min: { value: 0, message: 'Average service price cannot be negative' },
                max: { value: 10000, message: 'Average service price cannot exceed 10000 EUR' },
              })}
              className={`input-field ${errors.averageServicePrice ? 'border-red-500' : ''}`}
              placeholder="e.g., 150.00"
            />
            {errors.averageServicePrice && (
              <p className="mt-1 text-sm text-red-600">{errors.averageServicePrice.message}</p>
            )}
          </div>

          {/* Car Price */}
          <div>
            <label className="form-label">Car Price (EUR) *</label>
            <input
              type="number"
              step="0.01"
              {...register('price', {
                required: 'Car price is required',
                min: { value: 100, message: 'Car price must be at least 100 EUR' },
                max: { value: 1000000, message: 'Car price cannot exceed 1000000 EUR' },
              })}
              className={`input-field ${errors.price ? 'border-red-500' : ''}`}
              placeholder="e.g., 25000.00"
            />
            {errors.price && (
              <p className="mt-1 text-sm text-red-600">{errors.price.message}</p>
            )}
          </div>

          {/* Mileage */}
          <div>
            <label className="form-label">Mileage (km) *</label>
            <input
              type="number"
              {...register('mileage', {
                required: 'Mileage is required',
                min: { value: 0, message: 'Mileage cannot be negative' },
                max: { value: 1000000, message: 'Mileage cannot exceed 1000000 km' },
              })}
              className={`input-field ${errors.mileage ? 'border-red-500' : ''}`}
              placeholder="e.g., 50000"
            />
            {errors.mileage && (
              <p className="mt-1 text-sm text-red-600">{errors.mileage.message}</p>
            )}
          </div>
        </div>

        <div className="flex justify-end space-x-4 mt-8">
          <button
            type="button"
            onClick={() => navigate('/')}
            className="btn-secondary"
          >
            Cancel
          </button>
          <button
            type="submit"
            disabled={createMutation.isPending || updateMutation.isPending}
            className="btn-primary flex items-center space-x-2"
          >
            <Save className="h-4 w-4" />
            <span>
              {createMutation.isPending || updateMutation.isPending
                ? 'Saving...'
                : isEditing
                ? 'Update Car'
                : 'Add Car'}
            </span>
          </button>
        </div>
      </form>
    </div>
  );
};

export default CarForm; 
