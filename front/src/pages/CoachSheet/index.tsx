import { useContext, useEffect, useState } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import { AxiosError } from 'axios';

import Frame from '@components/Frame';
import ReservationInfo from '@components/ReservationInfo';
import Sheet from '@components/Sheet';
import BackButton from '@components/BackButton';
import { UserStateContext } from '@context/UserProvider';
import { Reservation } from '@typings/domain';
import api from '@api/index';
import * as S from '@styles/common';

const CoachSheet = () => {
  const { userData } = useContext(UserStateContext);
  const { id: reservationId } = useParams();
  const { state: crewId } = useLocation();
  const [reservationInfo, setReservationInfo] = useState<Reservation>();

  useEffect(() => {
    (async () => {
      try {
        const { data } = await api.get(`/api/v2/crews/${crewId}/reservations/${reservationId}`, {
          headers: {
            Authorization: `Bearer ${userData?.token}`,
          },
        });
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
      <Sheet title="작성한 면담 내용" sheets={reservationInfo.sheets} isView />
      <BackButton />
    </Frame>
  );
};

export default CoachSheet;
