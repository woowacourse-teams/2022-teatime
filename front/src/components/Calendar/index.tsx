import { useParams } from 'react-router-dom';
import useSchedule from '@hooks/useSchedules';
import { CALENDAR_DATE_LENGTH, DAY_OF_WEEKS } from '@constants/index';
import DateBox from './DateBox';
import { YearMonthContainer, DateGrid, DayOfWeekBox, Month, Year } from './styles';

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
      <YearMonthContainer>
        <Year>{year}</Year>
        <Month>{month}</Month>
      </YearMonthContainer>
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
