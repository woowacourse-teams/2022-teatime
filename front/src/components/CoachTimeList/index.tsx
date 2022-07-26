import React, { useContext, useState } from 'react';
import api from '@api/index';
import Conditional from '@components/Conditional';
import { ScheduleDispatchContext, ScheduleStateContext } from '@context/ScheduleProvider';
import { TimeListContainer, TimeBox, ButtonContainer, CheckButton, ConfirmButton } from './styles';

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

  const handleEnrollSchedules = async () => {
    const selectedTimes = daySchedule.schedules.reduce((newArray, { isSelected, dateTime }) => {
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
      dispatch({ type: 'UPDATE_SCHEDULE', dateTimes: selectedTimes });
      alert('확정되었습니다.✅');
    } catch {
      alert('스케쥴 등록 실패');
    }
  };

  return (
    <div>
      <TimeListContainer>
        {daySchedule?.schedules.map((schedule) => {
          const time = schedule.dateTime.slice(11, 16);

          return (
            <React.Fragment key={schedule.id}>
              <TimeBox
                isPossible={schedule.isPossible}
                aria-disabled={schedule.isPossible}
                selected={schedule.isSelected ? true : false}
                onClick={() => handleClickTime(schedule.dateTime)}
              >
                {time}
              </TimeBox>
            </React.Fragment>
          );
        })}
      </TimeListContainer>

      <Conditional condition={daySchedule?.schedules.length !== 0}>
        <ButtonContainer>
          <CheckButton onClick={handleSelectedAll}>
            {isSelectedAll ? '전체 해제' : '전체 선택'}
          </CheckButton>
          <ConfirmButton onClick={handleEnrollSchedules}>확인</ConfirmButton>
        </ButtonContainer>
      </Conditional>
    </div>
  );
};

export default CoachTimeList;
