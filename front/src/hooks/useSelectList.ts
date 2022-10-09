import { useState } from 'react';

const useSelectList = (initialValue: string) => {
  const [selectedItem, setSeletedItem] = useState(initialValue);

  const handleSelectItem = (e: React.MouseEvent<HTMLElement>) => {
    const target = e.target as HTMLElement;
    if (target.tagName !== 'LI') return;
    setSeletedItem(target.id);
  };

  return { selectedItem, setSeletedItem, handleSelectItem };
};

export default useSelectList;
