import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';

import api from '@api/index';
import { setStorage } from '@utils/localStorage';
import { LOCAL_DB } from '@constants/index';

const Certification = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code') ?? '';

  useEffect(() => {
    (async () => {
      try {
        const { data } = await api.post('/api/auth/login', {
          code,
        });
        setStorage(LOCAL_DB.USER, data);
        navigate(`/${data.role.toLowerCase()}`, { replace: true });
      } catch (error) {
        console.log(error);
      }
    })();
  }, []);

  return <div>로그인 중...</div>;
};

export default Certification;
