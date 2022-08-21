import { createContext, useReducer, Dispatch } from 'react';

import type { DaySchedule, Schedule, ScheduleMap } from '@typings/domain';
import { getFormatDate } from '@utils/date';

// const timeArray = [
//   '10:00',
//   '10:30',
//   '11:00',
//   '11:30',
//   '12:00',
//   '12:30',
//   '13:00',
//   '13:30',
//   '14:00',
//   '14:30',
//   '15:00',
//   '15:30',
//   '16:00',
//   '16:30',
//   '17:00',
//   '17:30',
// ];

// const getAllTime = (date: string) => {
//   return timeArray.map((time, index) => ({
//     id: index,
//     dateTime: `${date}T${time}:00.000Z`,
//     isSelected: false,
//   }));
// };

type State = {
  // allMonthSchedule: ScheduleMap;
  availableMonthSchedule: ScheduleMap;
  // allDaySchdule: Schedule[];
  availableDaySchedule: Schedule[];
  // date: string;
};

type Action =
  // | {
  //     type: 'SET_ALL_MONTH_SCHEDULE';
  //     coachSchedules: DaySchedule[];
  //     lastDate: number;
  //     year: string;
  //     month: string;
  //   }
  | {
      type: 'SET_AVAILABLE_MONTH_SCHEDULE';
      coachSchedules: DaySchedule[];
    }
  | { type: 'SELECT_AVAILABLE_DATE'; day: number }
  // | { type: 'SELECT_DATE'; day: number; date: string }
  // | { type: 'SELECT_TIME'; dateTime: string }
  // | { type: 'SELECT_ALL_TIMES'; isSelectedAll: boolean }
  // | { type: 'UPDATE_SCHEDULE'; selectedTimes: string[]; selectedDay: number }
  | { type: 'RESERVATE_TIME'; scheduleId: number; selectedDay: number };

type ScheduleDispatch = Dispatch<Action>;

const reducer = (state: State, action: Action) => {
  switch (action.type) {
    // case 'SET_ALL_MONTH_SCHEDULE': {
    //   const initialMonthSchedule = Array.from({ length: action.lastDate }).reduce(
    //     (newObj: ScheduleMap, _, index) => {
    //       const currentDateFormat = getFormatDate(action.year, action.month, index + 1);
    //       newObj[index + 1] = getAllTime(currentDateFormat);
    //       return newObj;
    //     },
    //     {}
    //   );

    //   const availableMonthSchedule = action.coachSchedules.reduce(
    //     (newObj: ScheduleMap, { day, schedules }) => {
    //       const currentDateFormat = getFormatDate(action.year, action.month, day);
    //       const newSchedule = getAllTime(currentDateFormat).map((time) => {
    //         const sameTime = schedules.find((coachTime) => coachTime.dateTime === time.dateTime);
    //         if (sameTime) {
    //           sameTime.isSelected = sameTime.isPossible;
    //           return sameTime;
    //         }
    //         return time;
    //       });

    //       newObj[day] = newSchedule;
    //       return newObj;
    //     },
    //     {}
    //   );

    //   return {
    //     ...state,
    //     allMonthSchedule: {
    //       ...initialMonthSchedule,
    //       ...availableMonthSchedule,
    //     },
    //   };
    // }
    // case 'SET_AVAILABLE_MONTH_SCHEDULE': {
    //   const availableMonthSchedule = action.coachSchedules.reduce((newObj, { day, schedules }) => {
    //     newObj[day] = schedules;
    //     return newObj;
    //   }, {} as ScheduleMap);

    //   return { ...state, availableMonthSchedule };
    // }
    case 'SELECT_AVAILABLE_DATE': {
      const selectedDaySchedule = state.availableMonthSchedule[action.day];

      return { ...state, availableDaySchedule: selectedDaySchedule };
    }
    // case 'SELECT_DATE': {
    //   const selectedDaySchedule = state.allMonthSchedule[action.day];

    //   return { ...state, allDaySchdule: selectedDaySchedule, date: action.date };
    // }
    // case 'SELECT_TIME': {
    //   const selectedIndex = state.allDaySchdule.findIndex(
    //     (time: Schedule) => time.dateTime === action.dateTime
    //   );
    //   const newSchedules = [...state.allDaySchdule];
    //   newSchedules[selectedIndex].isSelected = !newSchedules[selectedIndex].isSelected;
    //   return { ...state, allDaySchdule: newSchedules };
    // }
    // case 'SELECT_ALL_TIMES': {
    //   const newSchedules = state.allDaySchdule.map((schedule) => {
    //     if (schedule.isPossible !== false) {
    //       schedule.isSelected = action.isSelectedAll ? false : true;
    //     }
    //     return schedule;
    //   });

    //   return { ...state, allDaySchdule: newSchedules };
    // }
    // case 'UPDATE_SCHEDULE': {
    //   const newDaySchedule = state.allMonthSchedule[action.selectedDay].map((daySchedule) => {
    //     if (action.selectedTimes.includes(daySchedule.dateTime)) {
    //       return {
    //         id: daySchedule.id,
    //         dateTime: daySchedule.dateTime,
    //         isPossible: true,
    //         isSelected: true,
    //       };
    //     }
    //     return { id: daySchedule.id, dateTime: daySchedule.dateTime, isSelected: false };
    //   });

    //   return {
    //     ...state,
    //     allMonthSchedule: { ...state.allMonthSchedule, [action.selectedDay]: newDaySchedule },
    //   };
    // }
    case 'RESERVATE_TIME': {
      const newDaySchedule = state.availableDaySchedule.map((time) => {
        if (time.id === action.scheduleId) {
          return { id: action.scheduleId, dateTime: time.dateTime, isPossible: false };
        }
        return time;
      });

      return {
        ...state,
        availableMonthSchedule: {
          ...state.availableMonthSchedule,
          [action.selectedDay]: newDaySchedule,
        },
        availableDaySchedule: newDaySchedule,
      };
    }
    default:
      return state;
  }
};

const initialState: State = {
  // allMonthSchedule: {},
  availableMonthSchedule: {},
  // allDaySchdule: [],
  availableDaySchedule: [],
  // date: '',
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
