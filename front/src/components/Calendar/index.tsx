import { useState } from 'react';
import { useParams } from 'react-router-dom';
import LeftArrow from '@assets/left-arrow.svg';
import RightArrow from '@assets/right-arrow.svg';
import useSchedule from '@hooks/useSchedules';
import { CALENDAR_DATE_LENGTH, DAY_OF_WEEKS } from '@constants/index';
import { ScheduleMap } from '@typings/domain';
import DateBox from './DateBox';
import { CalendarContainer, YearMonthContainer, DateGrid, DayOfWeekBox } from './styles';

const Calendar = () => {
  const { id } = useParams();
  const { monthYear, setMonthYear, schedules } = useSchedule(id);
  const { firstDOW, lastDate, year, month } = monthYear;
  const [selectedDay, setSelectedDay] = useState<number | null>(null);

  const monthSchedule = schedules?.reduce((newObj, { day, schedules }) => {
    newObj[day] = schedules;
    return newObj;
  }, {} as ScheduleMap);

  const dateBoxLength =
    firstDOW + lastDate < CALENDAR_DATE_LENGTH.MIN
      ? CALENDAR_DATE_LENGTH.MIN
      : CALENDAR_DATE_LENGTH.MAX;

  return (
    <CalendarContainer>
      <h1>날짜를 선택해주세요.</h1>
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

          return isOutOfCalendar ? (
            <DateBox key={index} />
          ) : (
            <DateBox
              key={index}
              date={date + 1}
              monthSchedule={monthSchedule?.[date + 1]}
              onClick={() => setSelectedDay(date + 1)}
              selectedDay={selectedDay}
            />
          );
        })}
      </DateGrid>
    </CalendarContainer>
  );
};

export default Calendar;
