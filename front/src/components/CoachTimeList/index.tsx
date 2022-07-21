import React from 'react';
import useCoachTimes from '@hooks/useCoachTimes';
import { TimeListContainer, TimeBox, ButtonContainer, CheckButton, ConfirmButton } from './styles';

const CoachTimeList = () => {
  const { schedules, isLoading, isError, handleClickTime, handleSelectedAll, isSelectedAll } =
    useCoachTimes();

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
          <ConfirmButton>확인</ConfirmButton>
        </ButtonContainer>
      )}
    </div>
  );
};

export default CoachTimeList;
