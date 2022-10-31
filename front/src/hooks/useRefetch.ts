import { useState } from 'react';

const useRefetch = () => {
  const [refetchCount, setRefetchCount] = useState(0);

  const refetch = () => {
    setRefetchCount((prev) => prev + 1);
  };

  return { refetchCount, refetch };
};

export default useRefetch;
