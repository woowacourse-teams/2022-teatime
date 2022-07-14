import React, { useContext, useState } from 'react';
import dayjs from 'dayjs';
import { ScheduleStateContext } from '@context/ScheduleProvider';
import Conditional from '@components/Conditional';
import Modal from '@components/Modal';
import CheckCircle from '@assets/check-circle.svg';
import { TimeListContainer, TimeBox, ReserveButtonWrapper } from './styles';

const TimeList = () => {
  const { schedules } = useContext(ScheduleStateContext);
  const [isModalOpen, setIsModalOpen] = useState(false);
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
                <button onClick={() => setIsModalOpen(true)}>예약하기</button>
              </ReserveButtonWrapper>
            </Conditional>
            <Conditional condition={selectedTimeId !== schedule.id}>
              <TimeBox onClick={() => handleClickTime(schedule.id)}>{time}</TimeBox>
            </Conditional>
          </React.Fragment>
        );
      })}
      {isModalOpen && (
        <Modal
          icon={CheckCircle}
          title="예약완료"
          content="면담 내용을 지금 작성 하시겠습니까?"
          firstButtonName="나중에"
          secondButtonName="작성하기"
        />
      )}
    </TimeListContainer>
  );
};

export default TimeList;
