import { useEffect, useState } from 'react';
import { useParams, useLocation, useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import Frame from '@components/Frame';
import ReservationInfo from '@components/ReservationInfo';
import Sheet from '@components/Sheet';
import BackButton from '@components/BackButton';
import { getCrewReservationByCoach } from '@api/crew';
import { logError } from '@utils/logError';
import type { ReservationByCoach } from '@typings/domain';
import { ROUTES } from '@constants/index';
import * as S from '@styles/common';

interface LocationState {
  state: {
    crewId: number;
  };
}

const CoachSheet = () => {
  const navigate = useNavigate();
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
          logError(error);
          navigate(ROUTES.ERROR);
          return;
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
