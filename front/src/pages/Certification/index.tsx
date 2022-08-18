import { useContext, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';

import useSnackbar from '@hooks/useSnackbar';
import { UserDispatchContext } from '@context/UserProvider';
import api from '@api/index';

const Certification = () => {
  const navigate = useNavigate();
  const showSnackbar = useSnackbar();
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

  return <div>로그인 중...</div>;
};

export default Certification;
