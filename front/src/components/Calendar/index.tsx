import { useState, useContext, useEffect } from 'react';
import { useParams } from 'react-router-dom';

import DateBox from '@components/DateBox';
import Conditional from '@components/Conditional';
import api from '@api/index';
import { ScheduleDispatchContext, ScheduleStateContext } from '@context/ScheduleProvider';
import { CALENDAR_DATE_LENGTH, DAY_NUMBER, DAY_OF_WEEKS } from '@constants/index';
import {
  getNewMonthYear,
  getMonthYearDetails,
  getFormatDate,
  convertToFullDate,
  getCurrentFullDate,
} from '@utils/index';
import type { MonthYear } from '@typings/domain';
import * as S from './styles';

import LeftArrow from '@assets/left-arrow.svg';
import LeftArrowDisabled from '@assets/left-arrow-disabled.svg';
import RightArrow from '@assets/right-arrow.svg';

interface CalendarProps {
  isCoach?: boolean;
  openTimeList: () => void;
  closeTimeList: () => void;
}

const Calendar = ({ isCoach, openTimeList, closeTimeList }: CalendarProps) => {
  const { id: coachId } = useParams();
  const currentDate = new Date();
  const currentMonthYear = getMonthYearDetails(currentDate);
  const [selectedDay, setSelectedDay] = useState<number | null>(null);
  const [monthYear, setMonthYear] = useState<MonthYear>(currentMonthYear);
  const { firstDOW, lastDate, year, month, startDate } = monthYear;
  const { monthSchedule } = useContext(ScheduleStateContext);
  const dispatch = useContext(ScheduleDispatchContext);

  const dateBoxLength =
    monthYear.firstDOW + monthYear.lastDate < CALENDAR_DATE_LENGTH.MIN
      ? CALENDAR_DATE_LENGTH.MIN
      : CALENDAR_DATE_LENGTH.MAX;

  const handleUpdateMonth = (increment: number) => {
    closeTimeList();
    setSelectedDay(null);
    setMonthYear((prev) => getNewMonthYear(prev, increment));
  };

  const handleClickDate = (day: number, isWeekend: boolean) => {
    if (isWeekend) return;

    dispatch({
      type: 'SELECT_DATE',
      day,
      date: getFormatDate(year, month, day),
    });
    openTimeList();
    setSelectedDay(day);
  };

  useEffect(() => {
    (async () => {
      try {
        const { data: coachSchedules } = await api.get(
          `/api/coaches/${coachId}/schedules?year=${year}&month=${month}`
        );

        dispatch({ type: 'SET_MONTH_SCHEDULE', coachSchedules, lastDate, year, month });
      } catch {
        alert('스케쥴 get 요청 실패');
      }
    })();
  }, [monthYear]);

  return (
    <S.CalendarContainer>
      <S.YearMonthContainer>
        <span>
          {year}년 {month}월
        </span>
        <div>
          <Conditional condition={startDate < currentDate}>
            <img src={LeftArrowDisabled} alt="이전 버튼 비활성화 아이콘" />
          </Conditional>
          <Conditional condition={startDate >= currentDate}>
            <img src={LeftArrow} alt="이전 버튼 아이콘" onClick={() => handleUpdateMonth(-1)} />
          </Conditional>
          <img src={RightArrow} alt="다음 버튼 아이콘" onClick={() => handleUpdateMonth(1)} />
        </div>
      </S.YearMonthContainer>
      <S.DateGrid>
        {DAY_OF_WEEKS.map((day) => (
          <S.DayOfWeekBox key={day}>{day}</S.DayOfWeekBox>
        ))}
        {Array.from({ length: dateBoxLength }, (_, index) => {
          const date = index - firstDOW + 1;
          const isOutOfCalendar = index < firstDOW || lastDate <= date - 1;
          const dayNumber = convertToFullDate(year, month, date).getDay();
          const isWeekend = dayNumber === DAY_NUMBER.SUNDAY || dayNumber === DAY_NUMBER.SATURDAY;
          const isPastDay = convertToFullDate(year, month, date) < getCurrentFullDate();

          return isOutOfCalendar ? (
            <DateBox key={index} />
          ) : (
            <DateBox
              key={index}
              date={date}
              daySchedule={monthSchedule[date - 1]?.schedules}
              onClick={() => handleClickDate(date, isWeekend)}
              selectedDay={selectedDay}
              currentDay={convertToFullDate(year, month, date)}
              isCoach={isCoach}
              isWeekend={isWeekend}
              isPastDay={isPastDay}
            />
          );
        })}
      </S.DateGrid>
    </S.CalendarContainer>
  );
};

export default Calendar;
