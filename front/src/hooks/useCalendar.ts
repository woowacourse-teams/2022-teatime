import { useState } from 'react';

import { getMonthYearDetails, getNewMonthYear } from '@utils/date';
import { CALENDAR_DATE_LENGTH } from '@constants/index';
import type { MonthYear } from '@typings/domain';

const useCalendar = () => {
  const currentDate = new Date();
  const currentMonthYear = getMonthYearDetails(currentDate);
  const [selectedDay, setSelectedDay] = useState<number>(0);
  const [monthYear, setMonthYear] = useState<MonthYear>(currentMonthYear);

  const dateBoxLength =
    monthYear.firstDOW + monthYear.lastDate <= CALENDAR_DATE_LENGTH.MIN
      ? CALENDAR_DATE_LENGTH.MIN
      : CALENDAR_DATE_LENGTH.MAX;

  const updateMonthYear = (increment: number) => {
    setSelectedDay(0);
    setMonthYear((prev) => getNewMonthYear(prev, increment));
  };

  return { monthYear, selectedDay, setSelectedDay, dateBoxLength, updateMonthYear };
};

export default useCalendar;
