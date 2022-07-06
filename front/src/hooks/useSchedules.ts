import { useState } from 'react';
import { ScheduleMap } from '@typings/domain';
import { getMonthYearDetails } from '@utils/index';
import dayjs from 'dayjs';
import useFetch from './useFetch';

const useSchedules = (id?: string) => {
  const currentMonthYear = getMonthYearDetails(dayjs());
  const [monthYear, setMonthYear] = useState(currentMonthYear);

  const { data: schedules } = useFetch<ScheduleMap>(
    `/coaches/${id}/schedules?year=${monthYear.year}&month=${monthYear.month}`
  );

  return { monthYear, setMonthYear, schedules };
};

export default useSchedules;
