import { useEffect } from 'react';
import { useSearchParams } from 'react-router-dom';

import api from '@api/index';

const Certification = () => {
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code') ?? '';

  useEffect(() => {
    (async () => {
      try {
        const { data } = await api.post('/api/auth/login', {
          code,
        });
        console.log('data', data);
      } catch (error) {
        console.log(error);
      }
    })();
  }, []);

  return <div></div>;
};

export default Certification;
