import { createContext, useReducer, Dispatch } from 'react';
import { Schedule } from '@typings/domain';

type State = {
  schedules: Schedule[];
};

type Action = { type: 'SET_SCHEDULES'; data: Schedule[] };

type ScheduleDispatch = Dispatch<Action>;

const reducer = (state: State, action: Action) => {
  switch (action.type) {
    case 'SET_SCHEDULES': {
      return { ...state, schedules: action.data };
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
