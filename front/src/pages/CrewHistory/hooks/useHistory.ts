import { useState } from 'react';
import type { CrewHistory, CrewHistoryStatus } from '@typings/domain';

const useHistory = () => {
  const [historyList, setHistoryList] = useState<CrewHistory[]>([]);

  const changeHistoryStatus = (reservationId: number, status: CrewHistoryStatus) => {
    setHistoryList((prevHistory) => {
      return prevHistory.map((history) => {
        if (history.reservationId === reservationId) {
          history.status = status;
        }
        return history;
      });
    });
  };

  const moveForefrontHistory = (reservationId: number) => {
    const copyHistoryList = [...historyList];
    const index = copyHistoryList.findIndex((history) => history.reservationId === reservationId);
    const changedHistory = copyHistoryList[index];
    copyHistoryList.splice(index, 1);
    copyHistoryList.unshift(changedHistory);
    setHistoryList(copyHistoryList);
  };

  return { historyList, setHistoryList, changeHistoryStatus, moveForefrontHistory };
};

export default useHistory;
