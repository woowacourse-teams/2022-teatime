import { useContext, useEffect, useState } from 'react';
import { useParams, useLocation, useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import Frame from '@components/Frame';
import ReservationInfo from '@components/ReservationInfo';
import Sheet from '@components/Sheet';
import BackButton from '@components/BackButton';
import { SnackbarContext } from '@context/SnackbarProvider';
import { Reservation } from '@typings/domain';
import { completeReservation } from '@api/reservation';
import { getCrewReservationByCoach } from '@api/crew';
import { ROUTES } from '@constants/index';
import * as S from '@styles/common';
import * as Styled from './styles';

interface LocationState {
  state: {
    crewId: number;
    hasCompleteButton?: boolean;
  };
}

const CoachSheet = () => {
  const navigate = useNavigate();
  const showSnackbar = useContext(SnackbarContext);
  const { id: reservationId } = useParams();
  const {
    state: { crewId, hasCompleteButton },
  } = useLocation() as LocationState;
  const [reservationInfo, setReservationInfo] = useState<Reservation>();

  const handleCompleteReservation = async () => {
    if (!confirm('면담을 완료하시겠습니까?')) return;

    try {
      await completeReservation(reservationId as string);
      showSnackbar({ message: '완료되었습니다. ✅' });
      navigate(ROUTES.COACH, { replace: true });
    } catch (error) {
      alert(error);
      console.log(error);
    }
  };

  useEffect(() => {
    (async () => {
      try {
        const { data } = await getCrewReservationByCoach(crewId, reservationId as string);
        setReservationInfo(data);
      } catch (error) {
        if (error instanceof AxiosError) {
          alert(error.response?.data?.message);
          console.log(error);
        }
      }
    })();
  }, []);

  if (!reservationInfo) return <></>;

  return (
    <Frame>
      <S.InfoContainer>
        <ReservationInfo
          image={reservationInfo.coachImage}
          name={reservationInfo.coachName}
          dateTime={reservationInfo.dateTime}
        />
      </S.InfoContainer>
      <Styled.SheetContainer>
        <Sheet title="작성한 면담 내용" sheets={reservationInfo.sheets} isView />
        {hasCompleteButton && (
          <Styled.CompleteButtonWrapper>
            <button onClick={handleCompleteReservation}>면담 완료하기</button>
          </Styled.CompleteButtonWrapper>
        )}
      </Styled.SheetContainer>
      <BackButton />
    </Frame>
  );
};

export default CoachSheet;
