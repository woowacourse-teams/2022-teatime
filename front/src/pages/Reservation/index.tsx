import { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import TimeList from '@components/TimeList';
import Calendar from '@components/Calendar';
import Frame from '@components/Frame';
import Title from '@components/Title';
import { ScheduleDispatchContext } from '@context/ScheduleProvider';
import { UserStateContext } from '@context/UserProvider';
import api from '@api/index';
import useTimeList from '@hooks/useTimeList';
import { CALENDAR_DATE_LENGTH } from '@constants/index';
import { getFormatDate, getMonthYearDetails, getNewMonthYear } from '@utils/date';
import { MonthYear } from '@typings/domain';
import theme from '@styles/theme';
import * as S from '@styles/common';

const Reservation = () => {
  const currentDate = new Date();
  const { isOpenTimeList, openTimeList, closeTimeList } = useTimeList();
  const { id: coachId } = useParams();
  const currentMonthYear = getMonthYearDetails(currentDate);
  const [selectedDay, setSelectedDay] = useState<number | null>(null);
  const [monthYear, setMonthYear] = useState<MonthYear>(currentMonthYear);
  const { firstDOW, lastDate, year, month } = monthYear;
  const dispatch = useContext(ScheduleDispatchContext);
  const { userData } = useContext(UserStateContext);

  const dateBoxLength =
    firstDOW + lastDate < CALENDAR_DATE_LENGTH.MIN
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
          `/api/v2/coaches/${coachId}/schedules?year=${year}&month=${month}`,
          {
            headers: {
              Authorization: `Bearer ${userData?.token}`,
            },
          }
        );
        dispatch({ type: 'SET_MONTH_SCHEDULE', coachSchedules, lastDate, year, month });
      } catch {
        alert('스케쥴 get 요청 실패');
      }
    })();
  }, [monthYear]);

  return (
    <Frame>
      <S.ScheduleContainer>
        <Title
          text="예약할"
          highlightText={isOpenTimeList ? '시간을' : '날짜를'}
          hightlightColor={theme.colors.GREEN_300}
          extraText="선택해주세요."
        />
        <S.CalendarContainer>
          <Calendar
            onUpdateMonth={handleUpdateMonth}
            onClickDate={handleClickDate}
            monthYear={monthYear}
            dateBoxLength={dateBoxLength}
            selectedDay={selectedDay}
          />
          {isOpenTimeList && <TimeList />}
        </S.CalendarContainer>
      </S.ScheduleContainer>
    </Frame>
  );
};

export default Reservation;
