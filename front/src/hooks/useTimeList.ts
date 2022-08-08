import { useState } from 'react';

const useTimeList = () => {
  const [isOpenTimeList, setIsOpenTimeList] = useState(false);

  const openTimeList = () => {
    setIsOpenTimeList(true);
  };

  const closeTimeList = () => {
    setIsOpenTimeList(false);
  };

  return { isOpenTimeList, openTimeList, closeTimeList };
};

export default useTimeList;
