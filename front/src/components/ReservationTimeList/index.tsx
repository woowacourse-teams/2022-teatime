import React, { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import Conditional from '@components/Conditional';
import Modal from '@components/Modal';
import useBoolean from '@hooks/useBoolean';
import { UserStateContext } from '@context/UserProvider';
import api from '@api/index';
import { ROUTES } from '@constants/index';
import { getHourMinutes } from '@utils/date';
import type { TimeSchedule } from '@typings/domain';
import * as S from './styles';

import CheckCircle from '@assets/check-circle.svg';

interface ReservationTimeListProps {
  daySchedule: TimeSchedule[];
  onReservationTime: (scheduleId: number) => void;
}

const ReservationTimeList = ({ daySchedule, onReservationTime }: ReservationTimeListProps) => {
  const navigate = useNavigate();
  const { userData } = useContext(UserStateContext);
  const { isOpen: isOpenModal, openElement: openModal, closeElement: closeModal } = useBoolean();
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
      onReservationTime(scheduleId);
      openModal();
    } catch (error) {
      if (error instanceof AxiosError) {
        alert(error.response?.data?.message);
        console.log(error);
      }
    }
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
                onClick={() => handleClickTime(schedule.id)}
              >
                {time}
              </S.TimeBox>
            </Conditional>
          </React.Fragment>
        );
      })}
      {isOpenModal && (
        <Modal
          icon={CheckCircle}
          title="예약완료"
          content="면담 내용을 지금 작성 하시겠습니까?"
          firstButtonName="나중에"
          secondButtonName="작성하기"
          onClickFirstButton={() => navigate(ROUTES.CREW_HISTORY)}
          onClickSecondButton={() => navigate(`${ROUTES.CREW_SHEET}/${reservationId}`)}
          closeModal={closeModal}
        />
      )}
    </S.TimeListContainer>
  );
};

export default ReservationTimeList;
