import { useState } from 'react';

const useSelectList = (initialValue: string) => {
  const [selectedItem, setSelectedItem] = useState(initialValue);

  const handleSelectItem = (e: React.MouseEvent<HTMLElement>) => {
    const target = e.target as HTMLElement;
    if (target.tagName !== 'LI') return;
    setSelectedItem(target.id);
  };

  return { selectedItem, setSelectedItem, handleSelectItem };
};

export default useSelectList;
