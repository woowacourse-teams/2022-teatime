import React, { useContext, useState } from 'react';
import dayjs from 'dayjs';
import { ScheduleStateContext } from '@context/ScheduleProvider';
import { TimeListContainer, TimeBox, ReserveButtonWrapper } from './styles';
import Conditional from '@components/Conditional';

const TimeList = () => {
  const { schedules } = useContext(ScheduleStateContext);
  const [selectedTimeId, setSelectedTimeId] = useState<number | null>(null);

  const handleClickTime = (id: number) => {
    setSelectedTimeId(id);
  };

  return (
    <TimeListContainer>
      {schedules.map((schedule) => {
        const time = dayjs(schedule.dateTime).format('H:mm');

        return (
          <React.Fragment key={schedule.id}>
            <Conditional condition={selectedTimeId === schedule.id}>
              <ReserveButtonWrapper>
                <div>{time}</div>
                <button>예약하기</button>
              </ReserveButtonWrapper>
            </Conditional>
            <Conditional condition={selectedTimeId !== schedule.id}>
              <TimeBox onClick={() => handleClickTime(schedule.id)}>{time}</TimeBox>
            </Conditional>
          </React.Fragment>
        );
      })}
    </TimeListContainer>
  );
};

export default TimeList;
