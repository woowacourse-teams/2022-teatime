import { useContext } from 'react';
import { Navigate, Outlet } from 'react-router-dom';

import NotFound from '@pages/NotFound';
import { UserStateContext } from '@context/UserProvider';
import { UserRole } from '@typings/domain';
import { ROUTES } from './constants';

interface AuthRouteProps {
  role: UserRole;
}

const AuthRoute = ({ role }: AuthRouteProps) => {
  const { userData } = useContext(UserStateContext);

  if (!userData) {
    return <Navigate to={ROUTES.HOME} replace />;
  }

  return role === userData.role ? <Outlet /> : <NotFound />;
};

export default AuthRoute;
