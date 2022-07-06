import DateBox from './DateBox';
import { CalendarTitle, DateGrid, DayOfWeekBox, Month, Year } from './styles';
import { DAY_OF_WEEKS } from '@constants/index';
import { useParams } from 'react-router-dom';
import useSchedule from '@hooks/useSchedules';

const Calendar = () => {
  const { id } = useParams();
  const { monthYear, setMonthYear, schedules } = useSchedule(id);
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
            <DateBox
              key={index}
              date={index - firstDOW + 1}
              schedules={schedules?.[index - firstDOW + 1]}
            />
          );
        })}
      </DateGrid>
    </>
  );
};

export default Calendar;
