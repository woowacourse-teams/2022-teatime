import { useEffect, useState } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import { AxiosError } from 'axios';

import Frame from '@components/Frame';
import ReservationInfo from '@components/ReservationInfo';
import Sheet from '@components/Sheet';
import BackButton from '@components/BackButton';
import { getCrewReservationByCoach } from '@api/crew';
import type { ReservationByCoach } from '@typings/domain';
import * as S from '@styles/common';

interface LocationState {
  state: {
    crewId: number;
  };
}

const CoachSheet = () => {
  const { id: reservationId } = useParams();
  const {
    state: { crewId },
  } = useLocation() as LocationState;
  const [reservationInfo, setReservationInfo] = useState<ReservationByCoach>();

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
          image={reservationInfo.crewImage}
          name={reservationInfo.crewName}
          dateTime={reservationInfo.dateTime}
        />
      </S.InfoContainer>
      <Sheet title="작성한 면담 내용" sheets={reservationInfo.sheets} isReadOnly />
      <BackButton />
    </Frame>
  );
};

export default CoachSheet;
