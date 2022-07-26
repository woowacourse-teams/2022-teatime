import { useState, useContext, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import dayjs from 'dayjs';
import LeftArrow from '@assets/left-arrow.svg';
import LeftArrowDisabled from '@assets/left-arrow-disabled.svg';
import RightArrow from '@assets/right-arrow.svg';
import useSchedule from '@hooks/useSchedules';
import { CALENDAR_DATE_LENGTH, DAY_NUMBER, DAY_OF_WEEKS } from '@constants/index';
import { ScheduleDispatchContext } from '@context/ScheduleProvider';
import { MonthYear, Schedule, ScheduleMap } from '@typings/domain';
import DateBox from '@components/DateBox';
import Conditional from '@components/Conditional';
import { CalendarContainer, YearMonthContainer, DateGrid, DayOfWeekBox } from './styles';
import {
  CoachScheduleDispatchContext,
  CoachScheduleStateContext,
} from '@context/CoachScheduleProvider';
import { getNewMonthYear, getMonthYearDetails } from '@utils/index';
import api from '@api/index';

interface CalendarProps {
  isCoach?: boolean;
}

const Calendar = ({ isCoach }: CalendarProps) => {
  const { id: coachId } = useParams();
  const currentDate = dayjs();
  const currentMonthYear = getMonthYearDetails(dayjs());
  const [selectedDay, setSelectedDay] = useState<number | null>(null);
  const [monthYear, setMonthYear] = useState<MonthYear>(currentMonthYear);
  const { firstDOW, lastDate, year, month, startDate } = monthYear;
  const { monthSchedule } = useContext(CoachScheduleStateContext);
  const dispatch = useContext(CoachScheduleDispatchContext);

  const updateMonth = (increment: number) => {
    setMonthYear((prev) => getNewMonthYear(prev, increment));
  };

  const dateBoxLength =
    monthYear.firstDOW + monthYear.lastDate < CALENDAR_DATE_LENGTH.MIN
      ? CALENDAR_DATE_LENGTH.MIN
      : CALENDAR_DATE_LENGTH.MAX;

  const monthScheduleMap = monthSchedule?.reduce((newObj, { day, schedules }) => {
    newObj[day] = schedules;
    return newObj;
  }, {} as ScheduleMap);

  const handleClickDate = (day: number, isWeekend: boolean) => {
    if (isWeekend) return;
    setSelectedDay(day);
    dispatch({
      type: 'CLICK_DATE',
      day,
      date: `${year}-${month}-${String(day).padStart(2, '0')}`,
    });
  };

  useEffect(() => {
    (async () => {
      try {
        const { data: coachSchedules } = await api.get(
          `/api/coaches/${coachId}/schedules?year=${year}&month=${month}`
        );

        dispatch({ type: 'SET_MONTH_SCHEDULE', data: coachSchedules, lastDate, year, month });
      } catch {
        alert('스케쥴 get요청 실패');
      }
    })();
  }, [monthYear]);

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
              daySchedule={monthScheduleMap[date]}
              onClick={() => handleClickDate(date, isWeekend)}
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
