import { useContext, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';
import { AxiosError } from 'axios';

import Loading from '@components/Loading';
import { UserDispatchContext } from '@context/UserProvider';
import { login } from '@api/auth';
import { ROUTES } from '@constants/index';

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
