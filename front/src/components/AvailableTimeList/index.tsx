import React, { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import Conditional from '@components/Conditional';
import Modal from '@components/Modal';
import useModal from '@hooks/useModal';
import { UserStateContext } from '@context/UserProvider';
import { ROUTES } from '@constants/index';
import { getHourMinutes } from '@utils/date';
import api from '@api/index';

import CheckCircle from '@assets/check-circle.svg';
import * as S from './styles';

interface TimeSchedule {
  id: number;
  dateTime: string;
  isPossible?: boolean;
  isSelected?: boolean;
}

interface AvailableTimeListProps {
  selectedDay: number;
  daySchedule: TimeSchedule[];
  reservateTime: (scheduleId: number) => void;
}

const AvailableTimeList = ({ daySchedule, reservateTime }: AvailableTimeListProps) => {
  const navigate = useNavigate();
  const { isModalOpen, openModal, closeModal } = useModal();
  const { userData } = useContext(UserStateContext);
  const [selectedTimeId, setSelectedTimeId] = useState<number | null>(null);
  const [reservationId, setReservationId] = useState<number | null>(null);

  const handleClickTime = (id: number) => {
    setSelectedTimeId(id);
  };

  const handleClickReservation = async (scheduleId: number) => {
    try {
      const data = await api.post(
        `/api/v2/reservations`,
        {
          scheduleId,
        },
        {
          headers: {
            Authorization: `Bearer ${userData?.token}`,
          },
        }
      );
      const location = data.headers.location.split('/').pop();
      setReservationId(Number(location));
      setSelectedTimeId(null);
      reservateTime(scheduleId);
      openModal();
    } catch (error) {
      alert(error);
      console.log(error);
    }
  };

  const handleClickWriteButton = () => {
    navigate(`${ROUTES.CREW_SHEET}/${reservationId}`);
  };

  return (
    <S.TimeListContainer>
      {daySchedule.map((schedule) => {
        const time = getHourMinutes(schedule.dateTime);

        return (
          <React.Fragment key={schedule.id}>
            <Conditional condition={selectedTimeId === schedule.id}>
              <S.ReserveButtonWrapper>
                <div>{time}</div>
                <button onClick={() => handleClickReservation(schedule.id)}>예약하기</button>
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
          onClickFirstButton={() => navigate(ROUTES.CREW)}
          onClickSecondButton={handleClickWriteButton}
          closeModal={closeModal}
        />
      )}
    </S.TimeListContainer>
  );
};

export default AvailableTimeList;
