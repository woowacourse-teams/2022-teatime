import { createContext, useReducer, Dispatch } from 'react';
import type { DaySchedule, Schedule as ScheduleType } from '@typings/domain';

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

type State = {
  monthSchedule: DaySchedule[];
  daySchedule: DaySchedule;
  date: string;
};

type Action =
  | {
      type: 'SET_MONTH_SCHEDULE';
      data: DaySchedule[];
      lastDate: number;
      year: string;
      month: string;
    }
  | { type: 'SELECT_DATE'; day: number; date: string }
  | { type: 'SELECT_TIME'; dateTime: string }
  | { type: 'SELECT_ALL_TIMES'; isSelectedAll: boolean }
  | { type: 'UPDATE_SCHEDULE'; dateTimes: string[] };

type ScheduleDispatch = Dispatch<Action>;

const reducer = (state: State, action: Action) => {
  switch (action.type) {
    case 'SET_MONTH_SCHEDULE': {
      const initialMonthSchedule = Array.from({ length: action.lastDate }, (_, index) => {
        return {
          day: index + 1,
          schedules: getAllTime(
            `${action.year}-${action.month}-${String(index + 1).padStart(2, '0')}`
          ),
        };
      });

      const newMonthSchedule = initialMonthSchedule.map((daySchedule: DaySchedule) => {
        const sameDaySchedule = action.data.find((schedule) => schedule.day === daySchedule.day);

        if (sameDaySchedule) {
          const newDaySchedule = daySchedule.schedules.map((time) => {
            const sameTimeSchedule = sameDaySchedule.schedules.find(
              (sameTime: ScheduleType) => sameTime.dateTime === time.dateTime
            );
            if (sameTimeSchedule) {
              return {
                id: sameTimeSchedule.id,
                dateTime: sameTimeSchedule.dateTime,
                isPossible: sameTimeSchedule.isPossible,
                isSelected: sameTimeSchedule.isPossible,
              };
            }

            return time;
          });

          return { day: daySchedule.day, schedules: newDaySchedule };
        }

        return daySchedule;
      });

      return { ...state, monthSchedule: newMonthSchedule };
    }
    case 'SELECT_DATE': {
      const selectedDaySchedule = state.monthSchedule.find(
        (daySchedule) => daySchedule.day === action.day
      ) as DaySchedule;

      return { ...state, daySchedule: selectedDaySchedule, date: action.date };
    }
    case 'SELECT_TIME': {
      const selectedIndex = state.daySchedule.schedules.findIndex(
        (time: ScheduleType) => time.dateTime === action.dateTime
      );
      const newSchedules = [...state.daySchedule.schedules];
      newSchedules[selectedIndex].isSelected = !newSchedules[selectedIndex].isSelected;

      return { ...state, daySchedule: { day: state.daySchedule.day, schedules: newSchedules } };
    }
    case 'SELECT_ALL_TIMES': {
      const newSchedules = state.daySchedule.schedules.map((schedule) => {
        if (schedule.isPossible !== false) {
          schedule.isSelected = action.isSelectedAll ? false : true;
        }
        return schedule;
      });

      return { ...state, daySchedule: { day: state.daySchedule.day, schedules: newSchedules } };
    }
    case 'UPDATE_SCHEDULE': {
      const newMonthSchedule = state.monthSchedule.map((daySchedule) => {
        if (daySchedule.day === state.daySchedule.day) {
          const newDaySchedule = daySchedule.schedules.map((time: ScheduleType) => {
            if (action.dateTimes.includes(time.dateTime)) {
              return { id: time.id, dateTime: time.dateTime, isPossible: true, isSelected: true };
            }
            return { id: time.id, dateTime: time.dateTime, isSelected: false };
          });

          return { day: daySchedule.day, schedules: newDaySchedule };
        }

        return daySchedule;
      });

      return { ...state, monthSchedule: newMonthSchedule };
    }
    default:
      return state;
  }
};

const initialState: State = {
  monthSchedule: [],
  daySchedule: { day: 0, schedules: [] },
  date: '',
};

export const ScheduleStateContext = createContext<State>(initialState);
export const ScheduleDispatchContext = createContext<ScheduleDispatch>(() => null);

const ScheduleProvider = ({ children }: { children: React.ReactNode }) => {
  const [scheduleData, dispatch] = useReducer(reducer, initialState);

  return (
    <ScheduleStateContext.Provider value={scheduleData}>
      <ScheduleDispatchContext.Provider value={dispatch}>
        {children}
      </ScheduleDispatchContext.Provider>
    </ScheduleStateContext.Provider>
  );
};

export default ScheduleProvider;
