import React, { useContext, useState } from 'react';
import { ScheduleStateContext } from '@context/ScheduleProvider';
import Conditional from '@components/Conditional';
import Modal from '@components/Modal';
import CheckCircle from '@assets/check-circle.svg';
import useModal from '@hooks/useModal';
import api from '@api/index';
import { TimeListContainer, TimeBox, ReserveButtonWrapper } from './styles';

const TimeList = () => {
  const { schedules } = useContext(ScheduleStateContext);
  const { isModalOpen, openModal, closeModal } = useModal();
  const [selectedTimeId, setSelectedTimeId] = useState<number | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [isError, setIsError] = useState(false);

  const handleClickTime = (id: number) => {
    setSelectedTimeId(id);
  };

  const handleClickReservationButton = async (scheduleId: number) => {
    try {
      setIsLoading(true);
      // await api.post(`/api/reservations`, {
      //   crewId: 2,
      //   coachId: 3,
      //   scheduleId,
      // });
      openModal();
    } catch (error) {
      setIsError(true);
    } finally {
      setIsLoading(false);
    }
  };

  if (isError) return <h1>error</h1>;

  return (
    <TimeListContainer>
      {schedules.map((schedule) => {
        const time = schedule.dateTime.slice(11, 16);

        return (
          <React.Fragment key={schedule.id}>
            <Conditional condition={selectedTimeId === schedule.id}>
              <ReserveButtonWrapper>
                <div>{time}</div>
                <button onClick={() => handleClickReservationButton(schedule.id)}>예약하기</button>
              </ReserveButtonWrapper>
            </Conditional>
            <Conditional condition={selectedTimeId !== schedule.id && !!schedule.isPossible}>
              <TimeBox onClick={() => handleClickTime(schedule.id)}>{time}</TimeBox>
            </Conditional>
            <Conditional condition={selectedTimeId !== schedule.id && !schedule.isPossible}>
              <TimeBox aria-disabled={false} disabled>
                {time}
              </TimeBox>
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
          closeModal={closeModal}
        />
      )}
    </TimeListContainer>
  );
};

export default TimeList;
