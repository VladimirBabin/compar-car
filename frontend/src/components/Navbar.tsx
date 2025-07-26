import { Link } from 'react-router-dom';
import { Car, Plus } from 'lucide-react';

const Navbar = () => {
  return (
    <nav className="bg-white shadow-lg">
      <div className="container mx-auto px-4">
        <div className="flex justify-between items-center h-16">
          <Link to="/" className="flex items-center space-x-2 text-xl font-bold text-primary-600">
            <Car className="h-8 w-8" />
            <span>Compar-Car</span>
          </Link>
          
          <div className="flex items-center space-x-4">
            <Link 
              to="/" 
              className="text-gray-700 hover:text-primary-600 transition-colors duration-200"
            >
              Car List
            </Link>
            <Link 
              to="/add" 
              className="btn-primary flex items-center space-x-2"
            >
              <Plus className="h-4 w-4" />
              <span>Add Car</span>
            </Link>
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar; 