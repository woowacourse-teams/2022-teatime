import { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import AvailableTimeList from '@components/AvailableTimeList';
import Calendar from '@components/Calendar';
import Frame from '@components/Frame';
import Title from '@components/Title';
import { UserStateContext } from '@context/UserProvider';
import api from '@api/index';
import useTimeList from '@hooks/useTimeList';
import { CALENDAR_DATE_LENGTH } from '@constants/index';
import { getMonthYearDetails, getNewMonthYear } from '@utils/date';
import type { DaySchedule, MonthYear, MonthScheduleMap, ScheduleInfo } from '@typings/domain';
import theme from '@styles/theme';
import * as S from '@styles/common';

const Reservation = () => {
  const { id: coachId } = useParams();
  const currentDate = new Date();
  const currentMonthYear = getMonthYearDetails(currentDate);
  const { isOpenTimeList, openTimeList, closeTimeList } = useTimeList();
  const { userData } = useContext(UserStateContext);
  const [schedule, setSchedule] = useState<Omit<ScheduleInfo, 'date'>>({
    monthSchedule: {},
    daySchedule: [],
  });
  const [selectedDay, setSelectedDay] = useState<number>(0);
  const [monthYear, setMonthYear] = useState<MonthYear>(currentMonthYear);
  const { firstDOW, lastDate, year, month } = monthYear;

  const dateBoxLength =
    firstDOW + lastDate < CALENDAR_DATE_LENGTH.MIN
      ? CALENDAR_DATE_LENGTH.MIN
      : CALENDAR_DATE_LENGTH.MAX;

  const createMapSchedule = (scheduleArray: DaySchedule[]) => {
    setSchedule((allSchedules) => {
      const newMonthSchedule = scheduleArray.reduce((newObj, { day, schedules }) => {
        newObj[day] = schedules;
        return newObj;
      }, {} as MonthScheduleMap);

      return {
        ...allSchedules,
        monthSchedule: newMonthSchedule,
      };
    });
  };

  const selectDaySchedule = (day: number) => {
    setSchedule((allSchedules) => {
      const selectedDaySchedule = schedule.monthSchedule[day];

      return {
        ...allSchedules,
        daySchedule: selectedDaySchedule,
      };
    });
  };

  const reservateTime = (scheduleId: number) => {
    setSchedule((allSchedules) => {
      const newDaySchedule = schedule.daySchedule.map((time) => {
        if (time.id === scheduleId) {
          return { id: scheduleId, dateTime: time.dateTime, isPossible: false };
        }
        return time;
      });

      return {
        ...allSchedules,
        monthSchedule: { ...schedule.monthSchedule, [selectedDay]: newDaySchedule },
        daySchedule: newDaySchedule,
      };
    });
  };

  const handleUpdateMonth = (increment: number) => {
    closeTimeList();
    setSelectedDay(0);
    setMonthYear((prev) => getNewMonthYear(prev, increment));
  };

  const handleClickDate = (day: number, isWeekend: boolean) => {
    if (isWeekend) return;

    openTimeList();
    selectDaySchedule(day);
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
        createMapSchedule(coachSchedules);
      } catch (error) {
        alert(error);
        console.log(error);
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
            monthSchedule={schedule.monthSchedule}
            monthYear={monthYear}
            dateBoxLength={dateBoxLength}
            selectedDay={selectedDay}
            onClickDate={handleClickDate}
            onUpdateMonth={handleUpdateMonth}
          />
          {isOpenTimeList && (
            <AvailableTimeList
              selectedDay={selectedDay}
              daySchedule={schedule.daySchedule}
              reservateTime={reservateTime}
            />
          )}
        </S.CalendarContainer>
      </S.ScheduleContainer>
    </Frame>
  );
};

export default Reservation;
