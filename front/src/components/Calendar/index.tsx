import { useParams } from 'react-router-dom';
import useSchedule from '@hooks/useSchedules';
import { CALENDAR_DATE_LENGTH, DAY_OF_WEEKS } from '@constants/index';
import DateBox from './DateBox';
import { CalendarTitle, DateGrid, DayOfWeekBox, Month, Year } from './styles';

const Calendar = () => {
  const { id } = useParams();
  const { monthYear, setMonthYear, schedules } = useSchedule(id);
  const { firstDOW, lastDate, year, month } = monthYear;

  const dateBoxLength =
    firstDOW + lastDate < CALENDAR_DATE_LENGTH.MIN
      ? CALENDAR_DATE_LENGTH.MIN
      : CALENDAR_DATE_LENGTH.MAX;
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
          const date = index - firstDOW;
          const isOutOfCalendar = index < firstDOW || lastDate <= date;

          return isOutOfCalendar ? (
            <DateBox key={index} />
          ) : (
            <DateBox key={index} date={date + 1} schedules={schedules?.[date + 1]} />
          );
        })}
      </DateGrid>
    </>
  );
};

export default Calendar;
