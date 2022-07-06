import { useState } from 'react';
import dayjs from 'dayjs';
import { MonthYear } from '@typings/domain';
import DateBox from './DateBox';
import { DateGrid } from './styles';

const getMonthYearDetails = (initialDate: dayjs.Dayjs): MonthYear => {
  const month = initialDate.format('MM');
  const year = initialDate.format('YYYY');
  const startDate = dayjs(`${year}${month}01`);
  const firstDOW = Number(startDate.format('d'));
  const lastDate = Number(startDate.clone().endOf('month').format('DD'));

  return { startDate, firstDOW, lastDate, month, year };
};

const Calendar = () => {
  const currentDate = dayjs();
  const currentMonthYear = getMonthYearDetails(currentDate);
  const [monthYear, setMonthYear] = useState(currentMonthYear);

  const { firstDOW, lastDate } = monthYear;
  const dateBoxLength = firstDOW + lastDate < 35 ? 35 : 42;

  return (
    <DateGrid>
      {Array.from({ length: dateBoxLength }, (_, index) => {
        const isOutOfCalendar = index < firstDOW || lastDate <= index - firstDOW;
        return isOutOfCalendar ? (
          <DateBox key={index} />
        ) : (
          <DateBox key={index} date={index - firstDOW + 1} />
        );
      })}
    </DateGrid>
  );
};

export default Calendar;
