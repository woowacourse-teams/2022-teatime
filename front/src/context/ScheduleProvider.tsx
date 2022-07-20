import { createContext, useReducer, Dispatch } from 'react';
import { Schedule } from '@typings/domain';

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
  return timeArray.map((time) => `${date}T${time}:00.000Z`);
};

interface CoachSchedule extends Schedule {
  isSelected?: boolean;
}

type State = {
  schedules: CoachSchedule[];
  date: string;
};

type Action =
  | { type: 'SET_ALL_SCHEDULES'; data: Schedule[]; date: string }
  | { type: 'SET_SCHEDULES'; data: Schedule[] }
  | { type: 'SELECT'; dateTime: string | Date }
  | { type: 'SELECT_ALL'; isSelectedAll: boolean };

type ScheduleDispatch = Dispatch<Action>;

const reducer = (state: State, action: Action) => {
  switch (action.type) {
    case 'SET_SCHEDULES': {
      return { ...state, schedules: action.data };
    }
    case 'SET_ALL_SCHEDULES': {
      const allTimes = getAllTime(action.date);
      const result = allTimes.map((time, index) => {
        const sameTimeSchedule = action.data.find((schedule) => schedule.dateTime === time);
        if (sameTimeSchedule?.dateTime === time) {
          return {
            id: index,
            dateTime: time,
            isPossible: sameTimeSchedule.isPossible,
            isSelected: sameTimeSchedule.isPossible,
          };
        }

        return { id: index, dateTime: time, isSelected: false };
      });

      return { ...state, schedules: result, date: action.date };
    }
    case 'SELECT': {
      const targetIndex = state.schedules.findIndex(
        (schedule) => schedule.dateTime === action.dateTime
      );
      const newSchedules = [...state.schedules];
      newSchedules[targetIndex].isSelected = !newSchedules[targetIndex].isSelected;

      return { ...state, schedules: newSchedules };
    }
    case 'SELECT_ALL': {
      const newSchedules = [...state.schedules].map((schedule) => {
        if (schedule.isPossible !== false) {
          schedule.isSelected = action.isSelectedAll ? false : true;
        }

        return schedule;
      });

      return { ...state, schedules: newSchedules };
    }
    default:
      return state;
  }
};

const initialState: State = {
  schedules: [],
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
