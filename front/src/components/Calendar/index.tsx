import { useState } from 'react';
import dayjs from 'dayjs';
import { getMonthYearDetails } from '@utils/index';
import DateBox from './DateBox';
import { CalendarTitle, DateGrid, DayOfWeekBox, Month, Year } from './styles';
import { DAY_OF_WEEKS } from '@constants/index';

const Calendar = () => {
  const currentDate = dayjs();
  const currentMonthYear = getMonthYearDetails(currentDate);
  const [monthYear, setMonthYear] = useState(currentMonthYear);

  const { firstDOW, lastDate, year, month } = monthYear;
  const dateBoxLength = firstDOW + lastDate < 35 ? 35 : 42;

  return (
    <>
      <CalendarTitle>
        <Year>{year}년</Year>
        <Month>{Number(month)}월</Month>
      </CalendarTitle>
      <DateGrid>
        {DAY_OF_WEEKS.map((day) => {
          return <DayOfWeekBox key={day}>{day}</DayOfWeekBox>;
        })}
        {Array.from({ length: dateBoxLength }, (_, index) => {
          const isOutOfCalendar = index < firstDOW || lastDate <= index - firstDOW;
          return isOutOfCalendar ? (
            <DateBox key={index} />
          ) : (
            <DateBox key={index} date={index - firstDOW + 1} />
          );
        })}
      </DateGrid>
    </>
  );
};

export default Calendar;
