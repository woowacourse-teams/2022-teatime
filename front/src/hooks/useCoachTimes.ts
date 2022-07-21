import { useContext, useState } from 'react';
import { ScheduleDispatchContext, ScheduleStateContext } from '@context/ScheduleProvider';
import api from '@api/index';

const useCoachTimes = () => {
  const { schedules, date } = useContext(ScheduleStateContext);
  const dispatch = useContext(ScheduleDispatchContext);
  const [isLoading, setIsLoading] = useState(false);
  const [isError, setIsError] = useState(false);
  const [isSelectedAll, setIsSelectedAll] = useState(false);

  const selectedTimes = schedules.reduce((newArray, { isSelected, dateTime }) => {
    if (isSelected) {
      newArray.push(dateTime);
    }
    return newArray;
  }, [] as string[]);

  const handleClickTime = (dateTime: string) => {
    dispatch({ type: 'SELECT', dateTime });
  };

  const handleSelectedAll = () => {
    dispatch({ type: 'SELECT_ALL', isSelectedAll });
    setIsSelectedAll((prev) => !prev);
  };

  const handleEnrollSchedules = async () => {
    try {
      setIsLoading(true);
      await api.put(`/api/coaches/1/schedules`, {
        date,
        schedules: selectedTimes,
      });
      alert('확정되었습니다.✅');
    } catch (error) {
      setIsError(true);
    } finally {
      setIsLoading(false);
    }
  };

  return {
    schedules,
    isLoading,
    isError,
    handleClickTime,
    handleSelectedAll,
    isSelectedAll,
  };
};

export default useCoachTimes;
