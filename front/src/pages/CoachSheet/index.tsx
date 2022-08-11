import { useParams, useNavigate, useLocation } from 'react-router-dom';

import Frame from '@components/Frame';
import ReservationInfo from '@components/ReservationInfo';
import Sheet from '@components/Sheet';
import useFetch from '@hooks/useFetch';
import { Reservation } from '@typings/domain';

const CoachSheet = () => {
  const navigate = useNavigate();
  const { id: reservationId } = useParams();
  const { state: crewId } = useLocation();

  const { data: reservationInfo } = useFetch<Reservation, null>(
    `/api/crews/${crewId}/reservations/${reservationId}`
  );

  if (!reservationInfo) return;

  return (
    <Frame>
      <ReservationInfo
        image={reservationInfo.coachImage}
        name={reservationInfo.coachName}
        dateTime={reservationInfo.dateTime}
        isView={true}
        onClick={() => navigate(-1)}
      />
      <Sheet title="면담 내용 작성" sheets={reservationInfo.sheets} isView={true} />
    </Frame>
  );
};

export default CoachSheet;
