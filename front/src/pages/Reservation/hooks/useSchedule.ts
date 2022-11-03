import { useState } from 'react';
import type { DaySchedule, MonthScheduleMap, ScheduleInfo } from '@typings/domain';

const useSchedule = (selectedDay: number) => {
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

  const updateTimeIsPossible = (scheduleId: number, isPossible: boolean) => {
    setSchedule((allSchedules) => {
      const newDaySchedule = schedule.daySchedule.map((time) => {
        if (time.id === scheduleId) {
          return { id: scheduleId, dateTime: time.dateTime, isPossible };
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

  return { schedule, setSchedule, createScheduleMap, selectDaySchedule, updateTimeIsPossible };
};

export default useSchedule;
