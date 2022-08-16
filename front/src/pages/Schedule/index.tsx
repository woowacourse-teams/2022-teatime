import { useContext, useEffect, useState } from 'react';

import Frame from '@components/Frame';
import Calendar from '@components/Calendar';
import Title from '@components/Title';
import CoachTimeList from '@components/CoachTimeList';
import useTimeList from '@hooks/useTimeList';
import { ScheduleDispatchContext } from '@context/ScheduleProvider';
import api from '@api/index';
import { getFormatDate, getMonthYearDetails, getNewMonthYear } from '@utils/index';
import { getStorage } from '@utils/localStorage';
import { CALENDAR_DATE_LENGTH, LOCAL_DB } from '@constants/index';
import { MonthYear } from '@typings/domain';
import * as S from '@styles/common';

const Schedule = () => {
  const currentDate = new Date();
  const { isOpenTimeList, openTimeList, closeTimeList } = useTimeList();
  const currentMonthYear = getMonthYearDetails(currentDate);
  const [selectedDay, setSelectedDay] = useState<number | null>(null);
  const [monthYear, setMonthYear] = useState<MonthYear>(currentMonthYear);
  const { lastDate, year, month } = monthYear;
  const dispatch = useContext(ScheduleDispatchContext);
  const { token } = getStorage(LOCAL_DB.USER);

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
          `/api/v2/coaches/me/schedules?year=${year}&month=${month}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        dispatch({ type: 'SET_MONTH_SCHEDULE', coachSchedules, lastDate, year, month });
      } catch (error) {
        alert(error);
      }
    })();
  }, [monthYear]);

  return (
    <Frame>
      <S.ScheduleContainer>
        <Title text="가능한 날짜를 선택해주세요" />
        <S.CalendarContainer>
          <Calendar
            isCoach
            onUpdateMonth={handleUpdateMonth}
            onClickDate={handleClickDate}
            monthYear={monthYear}
            dateBoxLength={dateBoxLength}
            selectedDay={selectedDay}
          />
          {isOpenTimeList && <CoachTimeList />}
        </S.CalendarContainer>
      </S.ScheduleContainer>
    </Frame>
  );
};

export default Schedule;
