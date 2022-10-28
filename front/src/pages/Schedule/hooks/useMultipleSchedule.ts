import { useState } from 'react';
import { changeSelectedTime, timeArray } from '../utils/times';
import type { MultipleDaySchedule } from '@typings/domain';

const useMultipleSchedule = () => {
  const [selectedDayList, setSelectedDayList] = useState<MultipleDaySchedule>({
    dates: [],
    times: [],
  });

  const initSelectedMultipleDates = () => {
    setSelectedDayList((prev) => {
      return {
        ...prev,
        dates: [],
      };
    });
  };

  const initSelectedMultipleTimes = () => {
    setSelectedDayList((prev) => {
      const initTimes = timeArray.map((dateTime, index) => ({
        id: index,
        dateTime,
        isSelected: false,
      }));

      return {
        ...prev,
        times: initTimes,
      };
    });
  };

  const selectMultipleDays = (date: string) => {
    setSelectedDayList((prev) => {
      const newDates = [...selectedDayList.dates];
      const findIndex = newDates.findIndex((newDate) => newDate === date);

      if (newDates.includes(date)) {
        newDates.splice(findIndex, 1);
      } else {
        newDates.push(date);
      }

      return {
        ...prev,
        dates: newDates,
      };
    });
  };

  const handleClickMultipleTime = (dateTime: string) => {
    setSelectedDayList((prev) => {
      const newArray = changeSelectedTime(selectedDayList.times, dateTime);

      return {
        ...prev,
        times: newArray,
      };
    });
  };

  return {
    selectedDayList,
    initSelectedMultipleDates,
    initSelectedMultipleTimes,
    selectMultipleDays,
    handleClickMultipleTime,
  };
};

export default useMultipleSchedule;
