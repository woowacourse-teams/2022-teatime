import { useContext, useState } from 'react';

import Conditional from '@components/Conditional';
import { ScheduleDispatchContext, ScheduleStateContext } from '@context/ScheduleProvider';
import useSnackbar from '@hooks/useSnackbar';
import api from '@api/index';
import { getHourMinutes } from '@utils/index';
import { getStorage } from '@utils/localStorage';
import { LOCAL_DB } from '@constants/index';
import * as S from './styles';

const CoachTimeList = () => {
  const { token } = getStorage(LOCAL_DB.USER);
  const [isSelectedAll, setIsSelectedAll] = useState(false);
  const showSnackbar = useSnackbar();
  const { daySchedule, date } = useContext(ScheduleStateContext);
  const dispatch = useContext(ScheduleDispatchContext);

  const handleClickTime = (dateTime: string) => {
    dispatch({ type: 'SELECT_TIME', dateTime });
  };

  const handleSelectedAll = () => {
    dispatch({ type: 'SELECT_ALL_TIMES', isSelectedAll });
    setIsSelectedAll((prev) => !prev);
  };

  const handleUpdateSchedules = async () => {
    const selectedTimes = daySchedule.schedules.reduce((newArray, { isSelected, dateTime }) => {
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
            Authorization: `Bearer ${token}`,
          },
        }
      );

      dispatch({ type: 'UPDATE_SCHEDULE', dateTimes: selectedTimes });
      showSnackbar({ message: '확정되었습니다. ✅' });
    } catch (error) {
      alert('스케쥴 등록 실패');
      console.log(error);
    }
  };

  return (
    <S.TimeListContainer>
      <S.ScrollContainer>
        {daySchedule?.schedules.map((schedule) => {
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

      <Conditional condition={daySchedule?.schedules.length !== 0}>
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

export default CoachTimeList;
