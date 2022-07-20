import { useState } from 'react';
import dayjs from 'dayjs';
import { DaySchedule, MonthYear, ScheduleMap } from '@typings/domain';
import { getMonthYearDetails, getNewMonthYear } from '@utils/index';
import useFetch from './useFetch';
import { CALENDAR_DATE_LENGTH } from '@constants/index';

const useSchedules = (id?: string) => {
  const currentMonthYear = getMonthYearDetails(dayjs());
  const [monthYear, setMonthYear] = useState(currentMonthYear);

  const { data: schedules } = useFetch<DaySchedule[], MonthYear>(
    `/api/coaches/${id}/schedules?year=${monthYear.year}&month=${monthYear.month}`,
    monthYear
  );

  const monthSchedule = schedules?.reduce((newObj, { day, schedules }) => {
    newObj[day] = schedules;
    return newObj;
  }, {} as ScheduleMap);

  const updateMonth = (increment: number) => {
    setMonthYear((prev) => getNewMonthYear(prev, increment));
  };

  const dateBoxLength =
    monthYear.firstDOW + monthYear.lastDate < CALENDAR_DATE_LENGTH.MIN
      ? CALENDAR_DATE_LENGTH.MIN
      : CALENDAR_DATE_LENGTH.MAX;

  return { monthYear, updateMonth, monthSchedule, dateBoxLength };
};

export default useSchedules;
