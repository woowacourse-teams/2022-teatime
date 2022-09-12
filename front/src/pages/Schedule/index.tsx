import { useEffect, useState } from 'react';
import { AxiosError } from 'axios';

import Frame from '@components/Frame';
import Calendar from '@components/Calendar';
import Title from '@components/Title';
import ScheduleTimeList from '@components/ScheduleTimeList';
import useCalendar from '@hooks/useCalendar';
import useBoolean from '@hooks/useBoolean';
import { getCoachSchedulesByMe } from '@api/coach';
import { getFormatDate } from '@utils/date';
import type { DaySchedule, ScheduleInfo, MonthScheduleMap } from '@typings/domain';
import theme from '@styles/theme';
import * as S from '@styles/common';

const timeArray = [
  '10:00',
  '10:30',
  '11:00',
  '11:30',
  '12:00',
  '12:30',
  '13:00',
  '13:30',
  '14:00',
  '14:30',
  '15:00',
  '15:30',
  '16:00',
  '16:30',
  '17:00',
  '17:30',
];

const getAllTime = (date: string) => {
  return timeArray.map((time, index) => ({
    id: index,
    dateTime: `${date}T${time}:00.000Z`,
    isSelected: false,
  }));
};

const Schedule = () => {
  const { value: isOpenTimeList, setTrue: openTimeList, setFalse: closeTimeList } = useBoolean();
  const { monthYear, selectedDay, setSelectedDay, dateBoxLength, updateMonthYear } = useCalendar();
  const { lastDate, year, month } = monthYear;
  const [isSelectedAll, setIsSelectedAll] = useState(false);
  const [schedule, setSchedule] = useState<ScheduleInfo>({
    monthSchedule: {},
    daySchedule: [],
    date: '',
  });

  const selectDaySchedule = (day: number) => {
    setSchedule((allSchedules) => {
      const selectedDaySchedule = schedule.monthSchedule[day];
      const date = getFormatDate(year, month, day);

      return {
        ...allSchedules,
        daySchedule: selectedDaySchedule,
        date,
      };
    });
  };

  const createScheduleMap = (scheduleArray: DaySchedule[]) => {
    setSchedule((allSchedules) => {
      const initialMonthSchedule = Array.from({ length: lastDate }).reduce(
        (newObj: MonthScheduleMap, _, index) => {
          const currentDateFormat = getFormatDate(year, month, index + 1);
          newObj[index + 1] = getAllTime(currentDateFormat);
          return newObj;
        },
        {}
      );

      const availableMonthSchedule = scheduleArray.reduce(
        (newObj: MonthScheduleMap, { day, schedules }) => {
          const currentDateFormat = getFormatDate(year, month, day);
          const newSchedule = getAllTime(currentDateFormat).map((time) => {
            const sameTime = schedules.find((coachTime) => coachTime.dateTime === time.dateTime);
            if (sameTime) {
              sameTime.isSelected = sameTime.isPossible;
              return sameTime;
            }
            return time;
          });

          newObj[day] = newSchedule;
          return newObj;
        },
        {}
      );

      return {
        ...allSchedules,
        monthSchedule: { ...initialMonthSchedule, ...availableMonthSchedule },
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
    setIsSelectedAll(false);
  };

  const handleClickTime = (dateTime: string) => {
    setSchedule((allSchedules) => {
      const selectedIndex = schedule.daySchedule.findIndex((time) => time.dateTime === dateTime);
      const newSchedules = [...schedule.daySchedule];
      newSchedules[selectedIndex].isSelected = !newSchedules[selectedIndex].isSelected;

      return {
        ...allSchedules,
        daySchedule: newSchedules,
      };
    });
  };

  const handleSelectAll = () => {
    setIsSelectedAll((prev) => !prev);
    setSchedule((allSchedules) => {
      const newSchedules = schedule.daySchedule.map((schedule) => {
        if (schedule.isPossible !== false) {
          schedule.isSelected = !isSelectedAll;
        }
        return schedule;
      });

      return {
        ...allSchedules,
        daySchedule: newSchedules,
      };
    });
  };

  const handleUpdateDaySchedule = (selectedTimes: string[]) => {
    setSchedule((allSchedules) => {
      const newDaySchedule = schedule.monthSchedule[selectedDay].map(
        ({ id, dateTime, isPossible }) => {
          if (selectedTimes.includes(dateTime)) {
            return { id, dateTime, isPossible: true, isSelected: true };
          }
          if (isPossible === false) {
            return { id, dateTime, isPossible, isSelected: false };
          }
          return { id, dateTime, isSelected: false };
        }
      );

      return {
        ...allSchedules,
        monthSchedule: { ...schedule.monthSchedule, [selectedDay]: newDaySchedule },
      };
    });
  };

  useEffect(() => {
    const initSelectedTime = () => {
      setSchedule((allSchedules) => {
        const newDaySchedule = [...schedule.daySchedule];
        newDaySchedule.forEach((time) => (time.isSelected = time.isPossible));

        return {
          ...allSchedules,
          daySchedule: newDaySchedule,
        };
      });
    };

    initSelectedTime();
  }, [schedule.date]);

  useEffect(() => {
    (async () => {
      try {
        const { data: coachSchedules } = await getCoachSchedulesByMe(year, month);
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
          text="등록 가능한"
          highlightText={isOpenTimeList ? '시간을' : '날짜를'}
          hightlightColor={theme.colors.GREEN_300}
          extraText="선택해주세요."
        />
        <S.CalendarContainer>
          <Calendar
            isCoach
            monthSchedule={schedule.monthSchedule}
            monthYear={monthYear}
            dateBoxLength={dateBoxLength}
            selectedDay={selectedDay}
            onClickDate={handleClickDate}
            onUpdateMonth={handleUpdateMonth}
          />
          {isOpenTimeList && (
            <ScheduleTimeList
              isSelectedAll={isSelectedAll}
              date={schedule.date}
              daySchedule={schedule.daySchedule}
              onClickTime={handleClickTime}
              onSelectAll={handleSelectAll}
              onUpdateSchedule={handleUpdateDaySchedule}
            />
          )}
        </S.CalendarContainer>
      </S.ScheduleContainer>
    </Frame>
  );
};

export default Schedule;
