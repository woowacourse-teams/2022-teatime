import React, { useContext, useState } from 'react';
import { ScheduleDispatchContext } from '@context/ScheduleProvider';
import useCoachTimes from '@hooks/useCoachTimes';
import { TimeListContainer, TimeBox, ButtonContainer, CheckButton, ConfirmButton } from './styles';

const CoachTimeList = () => {
  const [isSelectedAll, setIsSelectedAll] = useState(false);
  const dispatch = useContext(ScheduleDispatchContext);
  const { schedules, isLoading, isError, handleEnrollSchedules } = useCoachTimes();

  const handleClickTime = (dateTime: string) => {
    dispatch({ type: 'SELECT', dateTime });
  };

  const handleSelectedAll = () => {
    dispatch({ type: 'SELECT_ALL', isSelectedAll });
    setIsSelectedAll((prev) => !prev);
  };

  if (isError) return <h1>error</h1>;

  return (
    <div>
      <TimeListContainer>
        {schedules.map((schedule) => {
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
      {schedules.length !== 0 && (
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
