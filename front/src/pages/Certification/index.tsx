import { useContext, useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';

import api from '@api/index';
import { UserDispatchContext } from '@context/UserProvider';

const Certification = () => {
  const navigate = useNavigate();
  const dispatch = useContext(UserDispatchContext);
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code') ?? '';

  useEffect(() => {
    (async () => {
      try {
        const { data } = await api.post('/api/auth/login', {
          code,
        });
        dispatch({ type: 'SET_USER', userData: data });
        navigate(`/${data.role.toLowerCase()}`, { replace: true });
      } catch (error) {
        console.log(error);
      }
    })();
  }, []);

  return <div>로그인 중...</div>;
};

export default Certification;
