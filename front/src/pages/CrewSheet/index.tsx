import { useParams, useNavigate } from 'react-router-dom';

import Frame from '@components/Frame';
import ReservationInfo from '@components/ReservationInfo';
import Sheet from '@components/Sheet';
import useFetch from '@hooks/useFetch';
import { Reservation, Sheets } from '@typings/domain';
import { ROUTES } from '@constants/index';
import api from '@api/index';
import * as S from '@styles/common';

const CrewSheet = () => {
  const navigate = useNavigate();
  const { id: reservationId } = useParams();

  const { data: reservationInfo } = useFetch<Reservation, null>(
    `/api/crews/me/reservations/${reservationId}`
  );
  const isView = reservationInfo?.status === 'SUBMITTED';

  const handleSubmit = async (isSubmitted: boolean, contents: Sheets[]) => {
    try {
      await api.put(`/api/crews/me/reservations/${reservationId}`, {
        status: isSubmitted ? 'SUBMITTED' : 'WRITING',
        sheets: contents,
      });
      alert('제출 되었습니다✅');
      navigate(ROUTES.CREW);
    } catch (error) {
      alert('제출 실패🚫');
    }
  };

  if (!reservationInfo) return <></>;

  return (
    <Frame>
      <S.InfoContainer>
        <ReservationInfo
          image={reservationInfo.coachImage}
          name={reservationInfo.coachName}
          dateTime={reservationInfo.dateTime}
          isView={isView}
          onClick={() => navigate(-1)}
        />
      </S.InfoContainer>
      <Sheet
        title="면담 내용 작성"
        sheets={reservationInfo.sheets}
        handleSubmit={handleSubmit}
        isView={isView}
      />
    </Frame>
  );
};

export default CrewSheet;
