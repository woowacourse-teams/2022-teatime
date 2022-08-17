import { useEffect, useState } from 'react';
import { useParams, useLocation } from 'react-router-dom';

import Frame from '@components/Frame';
import ReservationInfo from '@components/ReservationInfo';
import Sheet from '@components/Sheet';
import { Reservation } from '@typings/domain';
import { getStorage } from '@utils/localStorage';
import { LOCAL_DB } from '@constants/index';
import api from '@api/index';
import * as S from '@styles/common';

const CoachSheet = () => {
  const { token } = getStorage(LOCAL_DB.USER);
  const { id: reservationId } = useParams();
  const { state: crewId } = useLocation();
  const [reservationInfo, setReservationInfo] = useState<Reservation>();

  useEffect(() => {
    (async () => {
      try {
        const { data } = await api.get(`/api/v2/crews/${crewId}/reservations/${reservationId}`, {
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
          isView
        />
      </S.InfoContainer>
      <Sheet title="작성한 면담 내용" sheets={reservationInfo.sheets} isView />
    </Frame>
  );
};

export default CoachSheet;
