import { useContext, useState } from 'react';

import Conditional from '@components/Conditional';
import { ScheduleDispatchContext, ScheduleStateContext } from '@context/ScheduleProvider';
import { UserStateContext } from '@context/UserProvider';
import useSnackbar from '@hooks/useSnackbar';
import api from '@api/index';
import { getHourMinutes } from '@utils/date';
import * as S from './styles';

interface AllTimeListProps {
  selectedDay: number;
}

const AllTimeList = ({ selectedDay }: AllTimeListProps) => {
  const showSnackbar = useSnackbar();
  const { userData } = useContext(UserStateContext);
  const { allDaySchdule, date } = useContext(ScheduleStateContext);
  const dispatch = useContext(ScheduleDispatchContext);
  const [isSelectedAll, setIsSelectedAll] = useState(false);

  const handleClickTime = (dateTime: string) => {
    dispatch({ type: 'SELECT_TIME', dateTime });
  };

  const handleSelectedAll = () => {
    dispatch({ type: 'SELECT_ALL_TIMES', isSelectedAll });
    setIsSelectedAll((prev) => !prev);
  };

  const handleUpdateSchedules = async () => {
    const selectedTimes = allDaySchdule.reduce((newArray, { isSelected, dateTime }) => {
      if (isSelected) {
        newArray.push(dateTime);
      }
      return newArray;
    }, [] as string[]);

    try {
      await api.put(
        `/api/v2/coaches/me/schedules`,
        {
          date,
          schedules: selectedTimes,
        },
        {
          headers: {
            Authorization: `Bearer ${userData?.token}`,
          },
        }
      );
      dispatch({ type: 'UPDATE_SCHEDULE', selectedTimes, selectedDay });
      showSnackbar({ message: '확정되었습니다. ✅' });
    } catch (error) {
      alert('스케쥴 등록 실패');
      console.log(error);
    }
  };

  return (
    <S.TimeListContainer>
      <S.ScrollContainer>
        {allDaySchdule.map((schedule) => {
          const time = getHourMinutes(schedule.dateTime);

          return (
            <S.TimeBox
              key={schedule.id}
              isPossible={schedule.isPossible}
              aria-disabled={schedule.isPossible}
              selected={schedule.isSelected ? true : false}
              onClick={() => handleClickTime(schedule.dateTime)}
            >
              {time}
            </S.TimeBox>
          );
        })}
      </S.ScrollContainer>

      <Conditional condition={allDaySchdule.length !== 0}>
        <S.ButtonContainer>
          <S.CheckButton onClick={handleSelectedAll}>
            {isSelectedAll ? '전체 해제' : '전체 선택'}
          </S.CheckButton>
          <S.ConfirmButton onClick={handleUpdateSchedules}>확인</S.ConfirmButton>
        </S.ButtonContainer>
      </Conditional>
    </S.TimeListContainer>
  );
};

export default AllTimeList;
