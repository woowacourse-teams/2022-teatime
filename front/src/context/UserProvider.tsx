import { createContext, Dispatch, useReducer } from 'react';

import { getStorage, setStorage } from '@utils/localStorage';
import { LOCAL_DB } from '@constants/index';
import { UserInfo } from '@typings/domain';

type State = {
  userData: UserInfo | null;
};

type Action = { type: 'SET_USER'; userData: UserInfo };

type UserDispatch = Dispatch<Action>;

const reducer = (state: State, action: Action) => {
  switch (action.type) {
    case 'SET_USER': {
      const userData = action.userData;
      setStorage(LOCAL_DB.USER, userData);

      return {
        ...state,
        userData,
      };
    }
    default:
      return state;
  }
};

const initialState: State = {
  userData: getStorage(LOCAL_DB.USER),
};

export const UserStateContext = createContext<State>(initialState);
export const UserDispatchContext = createContext<UserDispatch>(() => null);

const UserProvider = ({ children }: { children: React.ReactNode }) => {
  const [userData, dispatch] = useReducer(reducer, initialState);

  return (
    <UserStateContext.Provider value={userData}>
      <UserDispatchContext.Provider value={dispatch}>{children}</UserDispatchContext.Provider>
    </UserStateContext.Provider>
  );
};

export default UserProvider;
