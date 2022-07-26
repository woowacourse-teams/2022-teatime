import React, { useContext, useState } from 'react';
import { TimeListContainer, TimeBox, ButtonContainer, CheckButton, ConfirmButton } from './styles';
import {
  CoachScheduleDispatchContext,
  CoachScheduleStateContext,
} from '@context/CoachScheduleProvider';
import api from '@api/index';

const CoachTimeList = () => {
  const [isSelectedAll, setIsSelectedAll] = useState(false);
  const { daySchedule, date } = useContext(CoachScheduleStateContext);
  const dispatch = useContext(CoachScheduleDispatchContext);

  const handleClickTime = (dateTime: string) => {
    dispatch({ type: 'SELECT_TIME', dateTime });
  };

  const handleSelectedAll = () => {
    // dispatch({ type: 'SELECT_ALL', isSelectedAll });
    setIsSelectedAll((prev) => !prev);
  };

  const handleEnrollSchedules = async () => {
    const selectedTimes = daySchedule?.schedules.reduce((newArray, { isSelected, dateTime }) => {
      if (isSelected) {
        newArray.push(dateTime);
      }
      return newArray;
    }, [] as string[]);

    try {
      await api.put(`/api/coaches/1/schedules`, {
        date,
        schedules: selectedTimes,
      });
      dispatch({ type: 'UPDATE_TIMES', dateTimes: selectedTimes });
      alert('확정되었습니다.✅');
    } catch {
      alert('스케쥴 등록 실패');
    }
  };

  return (
    <div>
      <TimeListContainer>
        {daySchedule?.schedules.map((schedule) => {
          return (
            <React.Fragment key={schedule.id}>
              <TimeBox
                isPossible={schedule.isPossible}
                aria-disabled={schedule.isPossible}
                onClick={() => handleClickTime(schedule.dateTime)}
                selected={schedule.isSelected ? true : false}
              >
                {schedule.dateTime.slice(11, 16)}
              </TimeBox>
            </React.Fragment>
          );
        })}
      </TimeListContainer>
      {daySchedule?.schedules.length !== 0 && (
        <ButtonContainer>
          <CheckButton onClick={handleSelectedAll}>
            {isSelectedAll ? '전체 해제' : '전체 선택'}
          </CheckButton>
          <ConfirmButton onClick={handleEnrollSchedules}>확인</ConfirmButton>
        </ButtonContainer>
      )}
    </div>
  );
};

export default CoachTimeList;
