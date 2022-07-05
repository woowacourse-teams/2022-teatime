import { useState, useEffect } from 'react';
import axios from 'axios';

interface UseFetch<T> {
  data: null | T;
  isLoading: boolean;
  isError: boolean;
}

const useFetch = <T>(url: string): UseFetch<T> => {
  const [data, setData] = useState<null | T>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [isError, setIsError] = useState(false);

  useEffect(() => {
    const requestData = async () => {
      setIsLoading(true);
      try {
        const { data } = await axios.get(url);
        setData(data);
      } catch (error) {
        setIsError(true);
      } finally {
        setIsLoading(false);
      }
    };
    requestData();
  }, []);

  return { data, isLoading, isError };
};

export default useFetch;
