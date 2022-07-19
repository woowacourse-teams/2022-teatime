import { useState } from 'react';
import { DaySchedule, MonthYear, ScheduleMap } from '@typings/domain';
import { getMonthYearDetails, getNewMonthYear } from '@utils/index';
import dayjs from 'dayjs';
import useFetch from './useFetch';

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

  return { monthYear, updateMonth, monthSchedule };
};

export default useSchedules;
