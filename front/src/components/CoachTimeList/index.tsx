import React, { useContext, useState } from 'react';
import { ScheduleDispatchContext, ScheduleStateContext } from '@context/ScheduleProvider';
import api from '@api/index';
import { TimeListContainer, TimeBox, ButtonContainer, CheckButton, ConfirmButton } from './styles';

const CoachTimeList = () => {
  const { schedules, date } = useContext(ScheduleStateContext);
  const dispatch = useContext(ScheduleDispatchContext);
  const [isSelectedAll, setIsSelectedAll] = useState(false);

  const handleClickTime = (dateTime: string) => {
    dispatch({ type: 'SELECT', dateTime });
  };

  const handleSelectedAll = () => {
    dispatch({ type: 'SELECT_ALL', isSelectedAll });
    setIsSelectedAll((prev) => !prev);
  };

  const selectedTimes = schedules.reduce((newArray, { isSelected, dateTime }) => {
    if (isSelected) {
      newArray.push(dateTime);
    }

    return newArray;
  }, [] as string[]);

  const handleEnrollSchedules = async () => {
    await api.put(`/api/coaches/1/schedules`, {
      date,
      schedules: selectedTimes,
    });

    alert('확정되었습니다.✅');
  };

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
