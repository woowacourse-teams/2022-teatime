import React, { useContext, useState } from 'react';
import * as S from './styles';
import dayjs from 'dayjs';

import Conditional from '@components/Conditional';
import { ScheduleDispatchContext, ScheduleStateContext } from '@context/ScheduleProvider';
import api from '@api/index';

const CoachTimeList = () => {
  const [isSelectedAll, setIsSelectedAll] = useState(false);
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
      await api.put(`/api/coaches/41/schedules`, {
        date,
        schedules: selectedTimes,
      });
      dispatch({ type: 'UPDATE_SCHEDULE', dateTimes: selectedTimes });
      alert('확정되었습니다.✅');
    } catch {
      alert('스케쥴 등록 실패');
    }
  };

  return (
    <S.TimeListContainer>
      <S.ScrollContainer>
        {daySchedule?.schedules.map((schedule) => {
          const time = dayjs.tz(schedule.dateTime).format('HH:mm');

          return (
            <React.Fragment key={schedule.id}>
              <S.TimeBox
                isPossible={schedule.isPossible}
                aria-disabled={schedule.isPossible}
                selected={schedule.isSelected ? true : false}
                onClick={() => handleClickTime(schedule.dateTime)}
              >
                {time}
              </S.TimeBox>
            </React.Fragment>
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
