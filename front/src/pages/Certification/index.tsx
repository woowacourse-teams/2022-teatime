import { useContext, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { AxiosError } from 'axios';

import Loading from '@components/Loading';
import { UserDispatchContext } from '@context/UserProvider';
import { login } from '@api/auth';
import { ERROR_MESSAGE, ROUTES } from '@constants/index';
import { logError } from '@utils/logError';

const Certification = () => {
  const navigate = useNavigate();
  const dispatch = useContext(UserDispatchContext);
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code') ?? '';

  useEffect(() => {
    (async () => {
      try {
        const { data: userData } = await login(code);
        dispatch({ type: 'SET_USER', userData });
        navigate(`/${userData.role.toLowerCase()}`, { replace: true });
      } catch (error) {
        if (error instanceof AxiosError) {
          alert(ERROR_MESSAGE.FAIL_LOGIN);
          navigate(ROUTES.HOME, { replace: true });
          logError(error);
        }
      }
    })();
  }, []);

  return <Loading />;
};

export default Certification;
