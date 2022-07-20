import { useState, useContext } from 'react';
import { useParams } from 'react-router-dom';
import dayjs from 'dayjs';
import LeftArrow from '@assets/left-arrow.svg';
import LeftArrowDisabled from '@assets/left-arrow-disabled.svg';
import RightArrow from '@assets/right-arrow.svg';
import useSchedule from '@hooks/useSchedules';
import { CALENDAR_DATE_LENGTH, DAY_NUMBER, DAY_OF_WEEKS } from '@constants/index';
import { ScheduleDispatchContext } from '@context/ScheduleProvider';
import { Schedule } from '@typings/domain';
import DateBox from '@components/DateBox';
import Conditional from '@components/Conditional';
import { CalendarContainer, YearMonthContainer, DateGrid, DayOfWeekBox } from './styles';

interface CalendarProps {
  isCoach?: boolean;
}

const Calendar = ({ isCoach }: CalendarProps) => {
  const { id } = useParams();
  const [selectedDay, setSelectedDay] = useState<number | null>(null);
  const dispatch = useContext(ScheduleDispatchContext);
  const { monthYear, updateMonth, monthSchedule } = useSchedule(id);
  const { firstDOW, lastDate, year, month, startDate } = monthYear;
  const currentDate = dayjs();

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
          <Conditional condition={startDate < currentDate}>
            <img src={LeftArrowDisabled} alt="이전 버튼 비활성화 아이콘" />
          </Conditional>
          <Conditional condition={startDate >= currentDate}>
            <img src={LeftArrow} alt="이전 버튼 아이콘" onClick={() => updateMonth(-1)} />
          </Conditional>
          <img src={RightArrow} alt="다음 버튼 아이콘" onClick={() => updateMonth(1)} />
        </div>
      </YearMonthContainer>
      <DateGrid>
        {DAY_OF_WEEKS.map((day) => {
          return <DayOfWeekBox key={day}>{day}</DayOfWeekBox>;
        })}
        {Array.from({ length: dateBoxLength }, (_, index) => {
          const date = index - firstDOW + 1;
          const isOutOfCalendar = index < firstDOW || lastDate <= date - 1;
          const dayNumber = dayjs(`${year}${month}${date - 1}`).day();
          const isWeekend = dayNumber === DAY_NUMBER.SATURDAY || dayNumber === DAY_NUMBER.SUNDAY;

          return isOutOfCalendar ? (
            <DateBox key={index} />
          ) : (
            <DateBox
              key={index}
              date={date}
              monthSchedule={monthSchedule?.[date]}
              onClick={() => handleClickDate(monthSchedule?.[date], date, isWeekend)}
              selectedDay={selectedDay}
              today={`${year}-${month}-${String(date).padStart(2, '0')}`}
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
