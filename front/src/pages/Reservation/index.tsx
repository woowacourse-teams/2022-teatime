import { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { AxiosError } from 'axios';

import ReservationTimeList from '@components/ReservationTimeList';
import Calendar from '@components/Calendar';
import Frame from '@components/Frame';
import Title from '@components/Title';
import useCalendar from '@hooks/useCalendar';
import useBoolean from '@hooks/useBoolean';
import { UserStateContext } from '@context/UserProvider';
import api from '@api/index';
import type { DaySchedule, MonthScheduleMap, ScheduleInfo } from '@typings/domain';
import theme from '@styles/theme';
import * as S from '@styles/common';

const Reservation = () => {
  const { id: coachId } = useParams();
  const { userData } = useContext(UserStateContext);
  const { monthYear, selectedDay, setSelectedDay, dateBoxLength, updateMonthYear } = useCalendar();
  const {
    isOpen: isOpenTimeList,
    openElement: openTimeList,
    closeElement: closeTimeList,
  } = useBoolean();

  const [selectedTimeId, setSelectedTimeId] = useState<number | null>(null);
  const [schedule, setSchedule] = useState<Omit<ScheduleInfo, 'date'>>({
    monthSchedule: {},
    daySchedule: [],
  });

  const createScheduleMap = (scheduleArray: DaySchedule[]) => {
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

  const handleReservationTime = (scheduleId: number) => {
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
    updateMonthYear(increment);
  };

  const handleClickDate = (day: number, isWeekend: boolean) => {
    if (isWeekend) return;

    openTimeList();
    selectDaySchedule(day);
    setSelectedDay(day);
    setSelectedTimeId(null);
  };

  const handleClickTime = (id: number | null) => {
    setSelectedTimeId(id);
  };

  useEffect(() => {
    (async () => {
      try {
        const { data: coachSchedules } = await api.get<DaySchedule[]>(
          `/api/v2/coaches/${coachId}/schedules?year=${monthYear.year}&month=${monthYear.month}`,
          {
            headers: {
              Authorization: `Bearer ${userData?.token}`,
            },
          }
        );
        createScheduleMap(coachSchedules);
      } catch (error) {
        if (error instanceof AxiosError) {
          alert(error.response?.data?.message);
          console.log(error);
        }
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
              daySchedule={schedule.daySchedule}
              onReservationTime={handleReservationTime}
              selectedTimeId={selectedTimeId}
              onClickTime={handleClickTime}
            />
          )}
        </S.CalendarContainer>
      </S.ScheduleContainer>
    </Frame>
  );
};

export default Reservation;
