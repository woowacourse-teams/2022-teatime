import React, { useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import Conditional from '@components/Conditional';
import Modal from '@components/Modal';
import useModal from '@hooks/useModal';
import { ScheduleDispatchContext, ScheduleStateContext } from '@context/ScheduleProvider';
import { ROUTES } from '@constants/index';
import { getHourMinutes } from '@utils/date';
import api from '@api/index';

import CheckCircle from '@assets/check-circle.svg';
import { UserStateContext } from '@context/UserProvider';
import * as S from './styles';

const TimeList = () => {
  const navigate = useNavigate();
  const { isModalOpen, openModal, closeModal } = useModal();
  const dispatch = useContext(ScheduleDispatchContext);
  const { daySchedule } = useContext(ScheduleStateContext);
  const { userData } = useContext(UserStateContext);
  const [selectedTimeId, setSelectedTimeId] = useState<number | null>(null);
  const [isError, setIsError] = useState(false);
  const [reservationId, setReservationId] = useState<number | null>(null);

  const coachSchedule = daySchedule.schedules.filter((time) => time.isPossible !== undefined);

  const handleClickTime = (id: number) => {
    setSelectedTimeId(id);
  };

  const handleClickReservationButton = async (scheduleId: number) => {
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
      dispatch({ type: 'RESERVATE_TIME', scheduleId });
      setReservationId(Number(location));
      setSelectedTimeId(null);
      openModal();
    } catch (error) {
      setIsError(true);
    }
  };

  const handleClickWriteButton = () => {
    navigate(`${ROUTES.CREW_SHEET}/${reservationId}`);
  };

  if (isError) return <h1>error</h1>;

  return (
    <S.TimeListContainer>
      {coachSchedule.map((schedule) => {
        const time = getHourMinutes(schedule.dateTime);

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
          onClickFirstButton={() => navigate(ROUTES.CREW)}
          onClickSecondButton={handleClickWriteButton}
          closeModal={closeModal}
        />
      )}
    </S.TimeListContainer>
  );
};

export default TimeList;
