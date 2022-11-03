import { useState } from 'react';

const useCategory = (initialValue: string) => {
  const [category, setCategory] = useState(initialValue);

  const handleChangeCategory = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setCategory(e.target.value);
  };

  return { category, handleChangeCategory };
};

export default useCategory;
