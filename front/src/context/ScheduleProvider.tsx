import { createContext, useReducer, Dispatch } from 'react';
import { Schedule } from '@typings/domain';

// 10시 ~ 5시30분
const timeArray = [
  '01:00',
  '01:30',
  '02:00',
  '02:30',
  '03:00',
  '03:30',
  '04:00',
  '04:30',
  '05:00',
  '05:30',
  '06:00',
  '06:30',
  '07:00',
  '07:30',
  '08:00',
  '08:30',
];

const getAllTime = (date: string) => {
  return timeArray.map((time) => `${date}T${time}:00.000Z`);
};

type State = {
  schedules: Schedule[] | Omit<Schedule, 'isPossible'>[];
};

type Action =
  | { type: 'SET_ALL_SCHEDULES'; data: Schedule[]; date: string }
  | { type: 'SET_SCHEDULES'; data: Schedule[] };

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
          return { id: index, dateTime: time, isPossible: sameTimeSchedule.isPossible };
        }

        return { id: index, dateTime: time };
      });

      return { ...state, schedules: result };
    }
    default:
      return state;
  }
};

const initialState: State = {
  schedules: [],
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
