import { useContext, useEffect, useState } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom';
import { AxiosError } from 'axios';

import Frame from '@components/Frame';
import ReservationInfo from '@components/ReservationInfo';
import Sheet from '@components/Sheet';
import BackButton from '@components/BackButton';
import { SnackbarContext } from '@context/SnackbarProvider';
import { editCrewReservation, getCrewCanceledReservation, getCrewReservationByMe } from '@api/crew';
import { ROUTES } from '@constants/index';
import type { Reservation, Sheets } from '@typings/domain';
import * as S from '@styles/common';

const CrewSheet = () => {
  const { id: reservationId } = useParams();
  const showSnackbar = useContext(SnackbarContext);
  const navigate = useNavigate();
  const { state: status } = useLocation();
  const [reservationInfo, setReservationInfo] = useState<Reservation>();

  const handleSubmit = async (isSubmitted: boolean, contents: Sheets[]) => {
    try {
      await editCrewReservation(reservationId as string, isSubmitted, contents);
      showSnackbar({ message: isSubmitted ? '제출되었습니다. 💌' : '임시 저장되었습니다. 📝' });
      navigate(ROUTES.CREW_HISTORY);
    } catch (error) {
      if (error instanceof AxiosError) {
        alert(error.response?.data?.message);
        console.log(error);
      }
    }
  };

  useEffect(() => {
    (async () => {
      try {
        const isCanceled = status === 'CANCELED';
        const { data } = isCanceled
          ? await getCrewCanceledReservation(reservationId as string)
          : await getCrewReservationByMe(reservationId as string);
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

  const isUnalterable = status === 'IN_PROGRESS' || status === 'DONE';
  const isView = reservationInfo.sheetStatus === 'SUBMITTED';

  return (
    <Frame>
      <S.InfoContainer>
        <ReservationInfo
          image={reservationInfo.coachImage}
          name={reservationInfo.coachName}
          dateTime={reservationInfo.dateTime}
        />
      </S.InfoContainer>
      <Sheet
        title="면담 내용 작성"
        sheets={reservationInfo.sheets}
        onSubmit={handleSubmit}
        isView={isView || isUnalterable}
      />
      <BackButton />
    </Frame>
  );
};

export default CrewSheet;
