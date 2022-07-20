import { useContext, useState } from 'react';
import { ScheduleStateContext } from '@context/ScheduleProvider';
import api from '@api/index';

const useCoachTimes = () => {
  const { schedules, date } = useContext(ScheduleStateContext);
  const [isLoading, setIsLoading] = useState(false);
  const [isError, setIsError] = useState(false);

  const selectedTimes = schedules.reduce((newArray, { isSelected, dateTime }) => {
    if (isSelected) {
      newArray.push(dateTime);
    }
    return newArray;
  }, [] as string[]);

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

  return { schedules, isLoading, isError, handleEnrollSchedules };
};

export default useCoachTimes;
