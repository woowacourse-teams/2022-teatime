import { useState, useEffect } from 'react';

import { api } from '@api/index';

interface UseFetch<T> {
  data: null | T;
  isLoading: boolean;
  isError: boolean;
}

const useFetch = <T, K>(url: string, dependency?: K): UseFetch<T> => {
  const [data, setData] = useState<null | T>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [isError, setIsError] = useState(false);

  useEffect(() => {
    const requestData = async () => {
      try {
        setIsLoading(true);
        const { data } = await api.get(url);
        setData(data);
      } catch (error) {
        setIsError(true);
        console.log(error);
      } finally {
        setIsLoading(false);
      }
    };
    requestData();
  }, [dependency]);

  return { data, isLoading, isError };
};

export default useFetch;
