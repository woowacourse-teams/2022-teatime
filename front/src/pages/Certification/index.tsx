import { useContext, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { AxiosError } from 'axios';

import Loading from '@components/Loading';
import { UserDispatchContext } from '@context/UserProvider';
import { SnackbarContext } from '@context/SnackbarProvider';
import { login } from '@api/auth';
import { ROUTES } from '@constants/index';

const Certification = () => {
  const navigate = useNavigate();
  const showSnackbar = useContext(SnackbarContext);
  const dispatch = useContext(UserDispatchContext);
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code') ?? '';

  useEffect(() => {
    (async () => {
      try {
        const { data: userData } = await login(code);
        dispatch({ type: 'SET_USER', userData });
        showSnackbar({ message: '로그인되었습니다. ✅' });
        navigate(`/${userData.role.toLowerCase()}`, { replace: true });
      } catch (error) {
        if (error instanceof AxiosError) {
          alert('에러가 발생했습니다. 다시 로그인해주세요.');
          navigate(ROUTES.HOME, { replace: true });
          console.log(error);
        }
      }
    })();
  }, []);

  return <Loading />;
};

export default Certification;
