import { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import TableRow from '@components/TableRow';
import { UserStateContext } from '@context/UserProvider';
import api from '@api/index';
import { ROUTES } from '@constants/index';
import { CrewHistory as CrewHistoryType } from '@typings/domain';
import { SnackbarContext } from '@context/SnackbarProvider';
import theme from '@styles/theme';
import * as S from './styles';

type StatusValue = { statusName: string; color: string; backgroundColor: string };

interface HistoryStatus {
  [key: string]: StatusValue;
}

const historyStatus: HistoryStatus = {
  BEFORE_APPROVED: {
    statusName: '승인전',
    color: theme.colors.ORANGE_600,
    backgroundColor: theme.colors.ORANGE_100,
  },
  APPROVED: {
    statusName: '승인완료',
    color: theme.colors.PURPLE_300,
    backgroundColor: theme.colors.PURPLE_100,
  },
  IN_PROGRESS: {
    statusName: '진행중',
    color: theme.colors.GREEN_700,
    backgroundColor: theme.colors.GREEN_100,
  },
  DONE: {
    statusName: '면담완료',
    color: theme.colors.GRAY_500,
    backgroundColor: theme.colors.GRAY_200,
  },
  CANCELED: {
    statusName: '면담취소',
    color: theme.colors.RED_400,
    backgroundColor: theme.colors.RED_100,
  },
};

const CrewHistory = () => {
  const { userData } = useContext(UserStateContext);
  const showSnackbar = useContext(SnackbarContext);
  const navigate = useNavigate();
  const [historyList, setHistoryList] = useState<CrewHistoryType[]>([]);

  const moveReservationSheet = (reservationId: number) => {
    navigate(`${ROUTES.CREW_SHEET}/${reservationId}`);
  };

  const deleteReservation = async (reservationId: number) => {
    if (!confirm('면담을 삭제하시겠습니까?')) return;

    try {
      await api.delete(`/api/v2/reservations/${reservationId}`, {
        headers: {
          Authorization: `Bearer ${userData?.token}`,
        },
      });
      setHistoryList((prevHistory) => {
        return prevHistory.filter((history) => history.reservationId !== reservationId);
      });
      showSnackbar({ message: '삭제되었습니다. 🗑' });
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
        const { data } = await api.get('/api/v2/crews/me/reservations', {
          headers: {
            Authorization: `Bearer ${userData?.token}`,
          },
        });
        setHistoryList(data);
      } catch (error) {
        if (error instanceof AxiosError) {
          alert(error.response?.data?.message);
          console.log(error);
        }
      }
    })();
  }, []);

  return (
    <S.Table>
      <S.Thead>
        <tr>
          <td>진행 상태</td>
          <td>코치</td>
          <td>날짜</td>
          <td>시간</td>
          <td />
        </tr>
      </S.Thead>
      <S.Tbody>
        {historyList.map((history) => {
          const { reservationId, status, coachName, coachImage, dateTime } = history;
          const { statusName, color, backgroundColor } = historyStatus[history.status];
          return (
            <TableRow
              key={history.reservationId}
              id={reservationId}
              status={status}
              name={coachName}
              image={coachImage}
              dateTime={dateTime}
              statusName={statusName}
              color={color}
              bgColor={backgroundColor}
              onClickSheet={moveReservationSheet}
              onClickDelete={deleteReservation}
              isCrew
            />
          );
        })}
      </S.Tbody>
    </S.Table>
  );
};

export default CrewHistory;
