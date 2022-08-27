import { useContext, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';

import Loading from '@components/Loading';
import { UserDispatchContext } from '@context/UserProvider';
import { SnackbarContext } from '@context/SnackbarProvider';
import api from '@api/index';

const Certification = () => {
  const navigate = useNavigate();
  const showSnackbar = useContext(SnackbarContext);
  const dispatch = useContext(UserDispatchContext);
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code') ?? '';

  useEffect(() => {
    (async () => {
      try {
        const { data: userData } = await api.post('/api/auth/login', {
          code,
        });
        dispatch({ type: 'SET_USER', userData });
        showSnackbar({ message: '로그인되었습니다. ✅' });
        navigate(`/${userData.role.toLowerCase()}`, { replace: true });
      } catch (error) {
        console.log(error);
      }
    })();
  }, []);

  return <Loading />;
};

export default Certification;
