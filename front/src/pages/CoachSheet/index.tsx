import { useParams, useNavigate, useLocation } from 'react-router-dom';

import Frame from '@components/Frame';
import ReservationInfo from '@components/ReservationInfo';
import Sheet from '@components/Sheet';
import useFetch from '@hooks/useFetch';
import { Reservation } from '@typings/domain';
import * as S from '@styles/common';

const CoachSheet = () => {
  const navigate = useNavigate();
  const { id: reservationId } = useParams();
  const { state: crewId } = useLocation();

  const { data: reservationInfo } = useFetch<Reservation, null>(
    `/api/crews/${crewId}/reservations/${reservationId}`
  );

  if (!reservationInfo) return <></>;

  return (
    <Frame>
      <S.InfoContainer>
        <ReservationInfo
          image={reservationInfo.coachImage}
          name={reservationInfo.coachName}
          dateTime={reservationInfo.dateTime}
          isView={true}
          onClick={() => navigate(-1)}
        />
      </S.InfoContainer>
      <Sheet title="작성한 면담 내용" sheets={reservationInfo.sheets} isView={true} />
    </Frame>
  );
};

export default CoachSheet;
