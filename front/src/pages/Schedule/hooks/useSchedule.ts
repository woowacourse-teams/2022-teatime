import { useState } from 'react';
import useCalendar from '@hooks/useCalendar';
import { getFormatDate } from '@utils/date';
import { changeSelectedTime, getAllTime } from '../utils/times';
import type { DaySchedule, MonthScheduleMap, ScheduleInfo } from '@typings/domain';

const useSchedule = () => {
  const { monthYear, selectedDay, setSelectedDay, dateBoxLength, updateMonthYear } = useCalendar();
  const { lastDate, year, month } = monthYear;
  const [schedule, setSchedule] = useState<ScheduleInfo>({
    monthSchedule: {},
    daySchedule: [],
    date: '',
  });
  const [isSelectedAll, setIsSelectedAll] = useState(false);

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

      const existingMonthSchedule = scheduleArray.reduce(
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
        monthSchedule: { ...initialMonthSchedule, ...existingMonthSchedule },
      };
    });
  };

  const selectDaySchedule = (day: number) => {
    setSelectedDay(day);
    setIsSelectedAll(false);
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

  const handleSelectAllTimes = () => {
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

  const updateAvailableTimes = (selectedTimes: string[]) => {
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

  const initSelectedTimes = () => {
    setSchedule((allSchedules) => {
      const newDaySchedule = [...schedule.daySchedule];
      newDaySchedule.forEach((time) => (time.isSelected = time.isPossible));

      return {
        ...allSchedules,
        daySchedule: newDaySchedule,
      };
    });
  };

  const handleClickTime = (dateTime: string) => {
    setSchedule((prev) => {
      const newArray = changeSelectedTime(schedule.daySchedule, dateTime);

      return {
        ...prev,
        daySchedule: newArray,
      };
    });
  };

  const updateMonthSchedule = (increment: number) => {
    setSchedule({ monthSchedule: {}, daySchedule: [], date: '' });
    updateMonthYear(increment);
  };

  return {
    schedule,
    isSelectedAll,
    setIsSelectedAll,
    createScheduleMap,
    selectDaySchedule,
    handleSelectAllTimes,
    updateAvailableTimes,
    initSelectedTimes,
    handleClickTime,
    monthYear,
    selectedDay,
    setSelectedDay,
    dateBoxLength,
    updateMonthSchedule,
  };
};

export default useSchedule;
