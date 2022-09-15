import { Fragment, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import Conditional from '@components/Conditional';
import Modal from '@components/Modal';
import useBoolean from '@hooks/useBoolean';
import { ROUTES } from '@constants/index';
import { createReservation } from '@api/reservation';
import { getHourMinutes } from '@utils/date';
import type { TimeSchedule } from '@typings/domain';
import * as S from './styles';

import CheckCircle from '@assets/check-circle.svg';

interface ReservationTimeListProps {
  daySchedule: TimeSchedule[];
  onReservationTime: (scheduleId: number) => void;
  selectedTimeId: number | null;
  onClickTime: (id: number | null) => void;
}

const ReservationTimeList = ({
  daySchedule,
  onReservationTime,
  selectedTimeId,
  onClickTime,
}: ReservationTimeListProps) => {
  const navigate = useNavigate();
  const { value: isOpenModal, setTrue: openModal, setFalse: closeModal } = useBoolean();
  const [reservationId, setReservationId] = useState<number | null>(null);

  const handleClickReservation = async (scheduleId: number) => {
    try {
      const data = await createReservation(scheduleId);
      const location = data.headers.location.split('/').pop();
      setReservationId(Number(location));
      onClickTime(null);
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
          <Fragment key={schedule.id}>
            <Conditional condition={selectedTimeId === schedule.id}>
              <S.ReserveButtonWrapper>
                <div>{time}</div>
                <button onClick={() => handleClickReservation(schedule.id)}>예약하기</button>
              </S.ReserveButtonWrapper>
            </Conditional>

            <Conditional condition={selectedTimeId !== schedule.id}>
              <S.TimeBox isPossible={schedule.isPossible} onClick={() => onClickTime(schedule.id)}>
                {time}
              </S.TimeBox>
            </Conditional>
          </Fragment>
        );
      })}
      {isOpenModal && (
        <Modal
          icon={CheckCircle}
          title="예약완료"
          firstButtonName="나중에"
          secondButtonName="작성하기"
          onClickFirstButton={() => navigate(ROUTES.CREW_HISTORY)}
          onClickSecondButton={() => navigate(`${ROUTES.CREW_SHEET}/${reservationId}`)}
          closeModal={closeModal}
        >
          <S.ModalContent>면담 내용을 지금 작성 하시겠습니까?</S.ModalContent>
        </Modal>
      )}
    </S.TimeListContainer>
  );
};

export default ReservationTimeList;
