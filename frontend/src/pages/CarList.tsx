import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import { Link } from 'react-router-dom';
import { Edit, Trash2, Filter, X } from 'lucide-react';
import { carApi } from '../services/api';
import { Car, CarFilter, BodyType, FuelType } from '../types/car';
import toast from 'react-hot-toast';

const CarList = () => {
  const [filters, setFilters] = useState<CarFilter>({
    page: 0,
    size: 20,
    sortBy: 'id',
    sortDirection: 'ASC'
  });
  const [showFilters, setShowFilters] = useState(false);

  const { data: carsData, isLoading, error, refetch } = useQuery({
    queryKey: ['cars', filters],
    queryFn: () => carApi.getCars(filters),
  });

  const { data: bodyTypes } = useQuery({
    queryKey: ['bodyTypes'],
    queryFn: () => carApi.getBodyTypes(),
  });

  const { data: fuelTypes } = useQuery({
    queryKey: ['fuelTypes'],
    queryFn: () => carApi.getFuelTypes(),
  });

  const handleDelete = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this car?')) {
      try {
        await carApi.deleteCar(id);
        toast.success('Car deleted successfully');
        refetch();
      } catch (error) {
        toast.error('Failed to delete car');
      }
    }
  };

  const updateFilters = (newFilters: Partial<CarFilter>) => {
    setFilters(prev => ({ ...prev, ...newFilters, page: 0 }));
  };

  const clearFilters = () => {
    setFilters({
      page: 0,
      size: 20,
      sortBy: 'id',
      sortDirection: 'ASC'
    });
  };

  const formatPrice = (price: number) => {
    return new Intl.NumberFormat('de-DE', {
      style: 'currency',
      currency: 'EUR'
    }).format(price);
  };

  if (error) {
    return (
      <div className="text-center py-8">
        <p className="text-red-600">Error loading cars. Please try again.</p>
      </div>
    );
  }

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold text-gray-900">Car List</h1>
        <div className="flex space-x-2">
          <button
            onClick={() => setShowFilters(!showFilters)}
            className="btn-secondary flex items-center space-x-2"
          >
            <Filter className="h-4 w-4" />
            <span>Filters</span>
          </button>
          <Link to="/add" className="btn-primary">
            Add New Car
          </Link>
        </div>
      </div>

      {/* Filters */}
      {showFilters && (
        <div className="bg-white p-6 rounded-lg shadow-md">
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
            <div>
              <label className="form-label">Model</label>
              <input
                type="text"
                value={filters.model || ''}
                onChange={(e) => updateFilters({ model: e.target.value })}
                className="input-field"
                placeholder="Search by model..."
              />
            </div>

            <div>
              <label className="form-label">Year From</label>
              <input
                type="number"
                value={filters.manufacturingYearFrom || ''}
                onChange={(e) => updateFilters({ manufacturingYearFrom: Number(e.target.value) })}
                className="input-field"
                placeholder="1900"
                min="1900"
                max="2030"
              />
            </div>

            <div>
              <label className="form-label">Year To</label>
              <input
                type="number"
                value={filters.manufacturingYearTo || ''}
                onChange={(e) => updateFilters({ manufacturingYearTo: Number(e.target.value) })}
                className="input-field"
                placeholder="2030"
                min="1900"
                max="2030"
              />
            </div>

            <div>
              <label className="form-label">Body Type</label>
              <select
                value={filters.bodyType || ''}
                onChange={(e) => updateFilters({ bodyType: e.target.value as BodyType })}
                className="input-field"
              >
                <option value="">All Body Types</option>
                {bodyTypes?.map((type) => (
                  <option key={type} value={type}>
                    {type.replace(/_/g, ' ')}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label className="form-label">Fuel Type</label>
              <select
                value={filters.fuelType || ''}
                onChange={(e) => updateFilters({ fuelType: e.target.value as FuelType })}
                className="input-field"
              >
                <option value="">All Fuel Types</option>
                {fuelTypes?.map((type) => (
                  <option key={type} value={type}>
                    {type.replace(/_/g, ' ')}
                  </option>
                ))}
              </select>
            </div>

            <div>
              <label className="form-label">Price From (EUR)</label>
              <input
                type="number"
                value={filters.priceFrom || ''}
                onChange={(e) => updateFilters({ priceFrom: Number(e.target.value) })}
                className="input-field"
                placeholder="0"
                min="0"
              />
            </div>

            <div>
              <label className="form-label">Price To (EUR)</label>
              <input
                type="number"
                value={filters.priceTo || ''}
                onChange={(e) => updateFilters({ priceTo: Number(e.target.value) })}
                className="input-field"
                placeholder="1000000"
                min="0"
              />
            </div>

            <div>
              <label className="form-label">Sort By</label>
              <select
                value={filters.sortBy || 'id'}
                onChange={(e) => updateFilters({ sortBy: e.target.value })}
                className="input-field"
              >
                <option value="id">ID</option>
                <option value="model">Model</option>
                <option value="manufacturingYear">Year</option>
                <option value="price">Price</option>
                <option value="mileage">Mileage</option>
              </select>
            </div>
          </div>

          <div className="flex justify-between items-center mt-4">
            <button
              onClick={clearFilters}
              className="btn-secondary flex items-center space-x-2"
            >
              <X className="h-4 w-4" />
              <span>Clear Filters</span>
            </button>
          </div>
        </div>
      )}

      {/* Car Table */}
      <div className="bg-white rounded-lg shadow-md overflow-hidden">
        {isLoading ? (
          <div className="p-8 text-center">
            <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-primary-600 mx-auto"></div>
            <p className="mt-2 text-gray-600">Loading cars...</p>
          </div>
        ) : (
          <>
            <div className="overflow-x-auto">
              <table className="min-w-full divide-y divide-gray-200">
                <thead className="bg-gray-50">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Model
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Year
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Engine
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Body
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Fuel
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Price
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Mileage
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Actions
                    </th>
                  </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                  {carsData?.content.map((car: Car) => (
                    <tr key={car.id} className="hover:bg-gray-50">
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">
                        {car.model}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                        {car.manufacturingYear}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                        {car.engineVolume}L
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                        {car.bodyType.replace(/_/g, ' ')}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                        {car.fuelType.replace(/_/g, ' ')}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                        {formatPrice(car.price)}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                        {car.mileage.toLocaleString()} km
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                        <div className="flex space-x-2">
                          <Link
                            to={`/edit/${car.id}`}
                            className="text-primary-600 hover:text-primary-900"
                          >
                            <Edit className="h-4 w-4" />
                          </Link>
                          <button
                            onClick={() => handleDelete(car.id!)}
                            className="text-red-600 hover:text-red-900"
                          >
                            <Trash2 className="h-4 w-4" />
                          </button>
                        </div>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>

            {/* Pagination */}
            {carsData && carsData.totalPages > 1 && (
              <div className="bg-white px-4 py-3 flex items-center justify-between border-t border-gray-200 sm:px-6">
                <div className="flex-1 flex justify-between sm:hidden">
                  <button
                    onClick={() => updateFilters({ page: Math.max(0, (filters.page || 0) - 1) })}
                    disabled={carsData.first}
                    className="relative inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 disabled:opacity-50"
                  >
                    Previous
                  </button>
                  <button
                    onClick={() => updateFilters({ page: (filters.page || 0) + 1 })}
                    disabled={carsData.last}
                    className="ml-3 relative inline-flex items-center px-4 py-2 border border-gray-300 text-sm font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 disabled:opacity-50"
                  >
                    Next
                  </button>
                </div>
                <div className="hidden sm:flex-1 sm:flex sm:items-center sm:justify-between">
                  <div>
                    <p className="text-sm text-gray-700">
                      Showing{' '}
                      <span className="font-medium">{carsData.number * carsData.size + 1}</span>
                      {' '}to{' '}
                      <span className="font-medium">
                        {Math.min((carsData.number + 1) * carsData.size, carsData.totalElements)}
                      </span>
                      {' '}of{' '}
                      <span className="font-medium">{carsData.totalElements}</span>
                      {' '}results
                    </p>
                  </div>
                  <div>
                    <nav className="relative z-0 inline-flex rounded-md shadow-sm -space-x-px">
                      <button
                        onClick={() => updateFilters({ page: Math.max(0, (filters.page || 0) - 1) })}
                        disabled={carsData.first}
                        className="relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50 disabled:opacity-50"
                      >
                        Previous
                      </button>
                      <button
                        onClick={() => updateFilters({ page: (filters.page || 0) + 1 })}
                        disabled={carsData.last}
                        className="relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50 disabled:opacity-50"
                      >
                        Next
                      </button>
                    </nav>
                  </div>
                </div>
              </div>
            )}
          </>
        )}
      </div>
    </div>
  );
};

export default CarList; 