import React, { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import * as S from './styles';
import dayjs from 'dayjs';

import Conditional from '@components/Conditional';
import Modal from '@components/Modal';
import useModal from '@hooks/useModal';
import { ScheduleStateContext } from '@context/ScheduleProvider';

import CheckCircle from '@assets/check-circle.svg';
import api from '@api/index';

const TimeList = () => {
  const { daySchedule } = useContext(ScheduleStateContext);
  const { isModalOpen, openModal, closeModal } = useModal();
  const [selectedTimeId, setSelectedTimeId] = useState<number | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [isError, setIsError] = useState(false);
  const navigate = useNavigate();

  const coachSchedule = daySchedule.schedules.filter((time) => time.isPossible !== undefined);

  const handleClickTime = (id: number) => {
    setSelectedTimeId(id);
  };

  const handleClickReservationButton = async (scheduleId: number) => {
    try {
      setIsLoading(true);
      await api.post(`/api/reservations`, {
        crewId: 17,
        coachId: 41,
        scheduleId,
      });
      openModal();
    } catch (error) {
      setIsError(true);
    } finally {
      setIsLoading(false);
    }
  };

  const handleClickWriteButton = () => {
    navigate(`/form/41`);
  };

  if (isError) return <h1>error</h1>;

  return (
    <S.TimeListContainer>
      {coachSchedule.map((schedule) => {
        const time = dayjs.tz(schedule.dateTime).format('HH:mm');

        return (
          <React.Fragment key={schedule.id}>
            <Conditional condition={selectedTimeId === schedule.id}>
              <S.ReserveButtonWrapper>
                <div>{time}</div>
                <button onClick={() => handleClickReservationButton(schedule.id)}>예약하기</button>
              </S.ReserveButtonWrapper>
            </Conditional>
            <Conditional condition={selectedTimeId !== schedule.id}>
              <S.TimeBox
                isPossible={schedule.isPossible}
                aria-disabled={schedule.isPossible}
                onClick={() => handleClickTime(schedule.id)}
              >
                {time}
              </S.TimeBox>
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
          onClick={handleClickWriteButton}
          closeModal={closeModal}
        />
      )}
    </S.TimeListContainer>
  );
};

export default TimeList;
