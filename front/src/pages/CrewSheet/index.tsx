import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

import Frame from '@components/Frame';
import ReservationInfo from '@components/ReservationInfo';
import Sheet from '@components/Sheet';
import { Reservation, Sheets } from '@typings/domain';
import { LOCAL_DB, ROUTES } from '@constants/index';
import { getStorage } from '@utils/localStorage';
import api from '@api/index';
import * as S from '@styles/common';

const CrewSheet = () => {
  const { token } = getStorage(LOCAL_DB.USER);
  const navigate = useNavigate();
  const { id: reservationId } = useParams();
  const [reservationInfo, setReservationInfo] = useState<Reservation>();

  const isView = reservationInfo?.status === 'SUBMITTED';

  const handleSubmit = async (isSubmitted: boolean, contents: Sheets[]) => {
    try {
      await api.put(
        `/api/v2/crews/me/reservations/${reservationId}`,
        {
          status: isSubmitted ? 'SUBMITTED' : 'WRITING',
          sheets: contents,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      alert('제출 되었습니다✅');
      navigate(ROUTES.CREW);
    } catch (error) {
      alert('제출 실패🚫');
    }
  };

  useEffect(() => {
    (async () => {
      try {
        const { data } = await api.get(`/api/v2/crews/me/reservations/${reservationId}`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setReservationInfo(data);
      } catch (error) {
        console.log(error);
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
          isView={isView}
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
