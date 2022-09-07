import { HOUR_MILLISECONDS } from '@constants/index';
import { MonthYear } from '@typings/domain';

type DateType = string | number;

const getFormatDate = (year: DateType, month: DateType, day: DateType) => {
  return `${year}-${month}-${String(day).padStart(2, '0')}`;
};

const getMonthYearDetails = (initialDate: Date) => {
  const month = String(initialDate.getMonth() + 1).padStart(2, '0');
  const year = String(initialDate.getFullYear());
  const startDate = new Date(`${year}-${month}`);
  const firstDOW = startDate.getDay();
  const lastDateString = String(
    new Date(initialDate.getFullYear(), initialDate.getMonth() + 1, 0).getDate()
  );
  const lastDate = Number(lastDateString.padStart(2, '0'));

  return { month, year, startDate, firstDOW, lastDate };
};

const getNewMonthYear = (prevData: MonthYear, increment: number) => {
  const newMonthYear = new Date(
    prevData.startDate.setMonth(prevData.startDate.getMonth() + increment)
  );

  return getMonthYearDetails(newMonthYear);
};

const getDateTime = (dateString: string) => {
  return new Date(new Date(dateString).getTime() - 9 * HOUR_MILLISECONDS);
};

const getHourMinutes = (dateString: string) => {
  const dateTime = getDateTime(dateString);
  const hour = String(dateTime.getHours()).padStart(2, '0');
  const minutes = String(dateTime.getMinutes()).padStart(2, '0');

  return `${hour}:${minutes}`;
};

const getMonthDate = (dateString: string) => {
  const dateTime = getDateTime(dateString);
  const month = String(dateTime.getMonth() + 1).padStart(2, '0');
  const date = String(dateTime.getDate()).padStart(2, '0');

  return `${month}월 ${date}일`;
};

const convertToFullDate = (year: DateType, month: DateType, date: DateType) => {
  return new Date(`${year}/${month}/${date}`);
};

const getCurrentFullDate = () => {
  return new Date(
    `${new Date().getFullYear()}/${new Date().getMonth() + 1}/${new Date().getDate()}`
  );
};

export {
  getMonthYearDetails,
  getNewMonthYear,
  getFormatDate,
  getDateTime,
  getHourMinutes,
  getMonthDate,
  convertToFullDate,
  getCurrentFullDate,
};
