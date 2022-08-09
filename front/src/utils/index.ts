import dayjs from 'dayjs';

import { MonthYear } from '@typings/domain';

type DateType = string | number;

const getMonthYearDetails = (initialDate: dayjs.Dayjs): MonthYear => {
  const month = initialDate.format('MM');
  const year = initialDate.format('YYYY');
  const startDate = dayjs(`${year}${month}01`);
  const firstDOW = Number(startDate.format('d'));
  const lastDate = Number(startDate.clone().endOf('month').format('DD'));

  return { startDate, firstDOW, lastDate, month, year };
};

const getNewMonthYear = (prevData: MonthYear, increment: number): MonthYear => {
  const newMonthYear = prevData.startDate.clone().add(increment, 'months');

  return getMonthYearDetails(newMonthYear);
};

const getFormatDate = (year: DateType, month: DateType, day: DateType) => {
  return `${year}-${month}-${String(day).padStart(2, '0')}`;
};

export { getMonthYearDetails, getNewMonthYear, getFormatDate };
