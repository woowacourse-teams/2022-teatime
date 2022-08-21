import { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import ReservationTimeList from '@components/ReservationTimeList';
import Calendar from '@components/Calendar';
import Frame from '@components/Frame';
import Title from '@components/Title';
import { UserStateContext } from '@context/UserProvider';
import api from '@api/index';
import useTimeList from '@hooks/useTimeList';
import useCalendar from '@hooks/useSchedule';
import type { DaySchedule, MonthScheduleMap, ScheduleInfo } from '@typings/domain';
import theme from '@styles/theme';
import * as S from '@styles/common';

const Reservation = () => {
  const { id: coachId } = useParams();
  const { userData } = useContext(UserStateContext);
  const { isOpenTimeList, openTimeList, closeTimeList } = useTimeList();
  const { monthYear, selectedDay, setSelectedDay, dateBoxLength, updateMonthYear } = useCalendar();
  const { year, month } = monthYear;
  const [schedule, setSchedule] = useState<Omit<ScheduleInfo, 'date'>>({
    monthSchedule: {},
    daySchedule: [],
  });

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
    // setSelectedDay(0);
    // setMonthYear((prev) => getNewMonthYear(prev, increment));
    updateMonthYear(increment);
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
            <ReservationTimeList
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
