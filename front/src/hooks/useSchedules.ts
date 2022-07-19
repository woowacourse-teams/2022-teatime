import { useState } from 'react';
import { DaySchedule, ScheduleMap } from '@typings/domain';
import { getMonthYearDetails } from '@utils/index';
import dayjs from 'dayjs';
import useFetch from './useFetch';

const useSchedules = (id?: string) => {
  const currentMonthYear = getMonthYearDetails(dayjs());
  const [monthYear, setMonthYear] = useState(currentMonthYear);

  const { data: schedules } = useFetch<DaySchedule[]>(
    `/api/coaches/${id}/schedules?year=${monthYear.year}&month=${monthYear.month}`
  );

  const monthSchedule = schedules?.reduce((newObj, { day, schedules }) => {
    newObj[day] = schedules;
    return newObj;
  }, {} as ScheduleMap);

  return { monthYear, setMonthYear, monthSchedule };
};

export default useSchedules;
