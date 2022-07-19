import { useState, useContext } from 'react';
import { useParams } from 'react-router-dom';
import dayjs from 'dayjs';
import LeftArrow from '@assets/left-arrow.svg';
import RightArrow from '@assets/right-arrow.svg';
import useSchedule from '@hooks/useSchedules';
import { CALENDAR_DATE_LENGTH, DAY_NUMBER, DAY_OF_WEEKS } from '@constants/index';
import { Schedule } from '@typings/domain';
import { ScheduleDispatchContext } from '@context/ScheduleProvider';
import DateBox from '@components/DateBox';
import { CalendarContainer, YearMonthContainer, DateGrid, DayOfWeekBox } from './styles';

interface CalendarProps {
  isCoach?: boolean;
}

const Calendar = ({ isCoach }: CalendarProps) => {
  const { id } = useParams();
  const [selectedDay, setSelectedDay] = useState<number | null>(null);
  const dispatch = useContext(ScheduleDispatchContext);
  const { monthYear, setMonthYear, monthSchedule } = useSchedule(id);
  const { firstDOW, lastDate, year, month } = monthYear;

  const today = dayjs().format('DD');

  const dateBoxLength =
    firstDOW + lastDate < CALENDAR_DATE_LENGTH.MIN
      ? CALENDAR_DATE_LENGTH.MIN
      : CALENDAR_DATE_LENGTH.MAX;

  const handleClickDate = (daySchedule: Schedule[] = [], date: number, isWeekend: boolean) => {
    if (isWeekend) return;

    setSelectedDay(date);
    if (isCoach) {
      dispatch({
        type: 'SET_ALL_SCHEDULES',
        data: daySchedule,
        date: `${year}-${month}-${String(date).padStart(2, '0')}`,
      });
      return;
    }

    dispatch({ type: 'SET_SCHEDULES', data: daySchedule });
  };

  return (
    <CalendarContainer>
      <YearMonthContainer>
        <span>
          {year}년 {month}월
        </span>
        <div>
          <img src={LeftArrow} alt="이전 버튼 아이콘" />
          <img src={RightArrow} alt="다음 버튼 아이콘" />
        </div>
      </YearMonthContainer>
      <DateGrid>
        {DAY_OF_WEEKS.map((day) => {
          return <DayOfWeekBox key={day}>{day}</DayOfWeekBox>;
        })}
        {Array.from({ length: dateBoxLength }, (_, index) => {
          const date = index - firstDOW;
          const isOutOfCalendar = index < firstDOW || lastDate <= date;
          const dayNumber = dayjs(`${year}${month}${date}`).day();
          const isWeekend = dayNumber === DAY_NUMBER.SATURDAY || dayNumber === DAY_NUMBER.SUNDAY;

          return isOutOfCalendar ? (
            <DateBox key={index} />
          ) : (
            <DateBox
              key={index}
              date={date + 1}
              monthSchedule={monthSchedule?.[date + 1]}
              onClick={() => handleClickDate(monthSchedule?.[date + 1], date + 1, isWeekend)}
              selectedDay={selectedDay}
              today={today}
              isCoach={isCoach}
              isWeekend={isWeekend}
            />
          );
        })}
      </DateGrid>
    </CalendarContainer>
  );
};

export default Calendar;
