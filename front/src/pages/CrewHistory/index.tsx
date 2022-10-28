import React, { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import TableRow from '@components/TableRow';
import EmptyContent from '@components/EmptyContent';
import Filter from '@components/Filter';
import useHistory from './hooks/useHistory';
import { SnackbarContext } from '@context/SnackbarProvider';
import { getCrewHistoriesByMe } from '@api/crew';
import { cancelReservation } from '@api/reservation';
import { ERROR_MESSAGE, ROUTES } from '@constants/index';
import type { CrewHistoryStatus } from '@typings/domain';
import { logError } from '@utils/logError';

import { theme } from '@styles/theme';
import * as S from './styles';

type StatusValue = { statusName: string; color: string; backgroundColor: string };

type HistoryStatus = {
  [key in CrewHistoryStatus]: StatusValue;
};

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
    color: theme.colors.RED_500,
    backgroundColor: theme.colors.RED_100,
  },
};

const CrewHistory = () => {
  const navigate = useNavigate();
  const showSnackbar = useContext(SnackbarContext);
  const { historyList, setHistoryList, changeHistoryStatus, moveForefrontHistory } = useHistory();
  const [category, setCategory] = useState<string>('ALL');

  const handleShowSheet = (reservationId: number, status: string) => () => {
    navigate(`${ROUTES.CREW_SHEET}/${reservationId}`, { state: status });
  };

  const handleCancelReservation = async (reservationId: number) => {
    if (!confirm('면담을 취소하시겠습니까?')) return;

    try {
      await cancelReservation(reservationId);
      changeHistoryStatus(reservationId, 'CANCELED');
      moveForefrontHistory(reservationId);
      showSnackbar({ message: '취소되었습니다. ❎' });
    } catch (error) {
      if (error instanceof AxiosError) {
        logError(error);
        alert(ERROR_MESSAGE.FAIL_CANCEL_RESERVATION);
        return;
      }
    }
  };

  const handleFilterStatus = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setCategory(e.target.value);
  };

  const filteredHistory = () => {
    if (category === 'ALL') {
      return historyList;
    }

    return historyList.filter((v) => v.status === category);
  };

  useEffect(() => {
    (async () => {
      try {
        const { data } = await getCrewHistoriesByMe();
        setHistoryList(data);
      } catch (error) {
        if (error instanceof AxiosError) {
          logError(error);
          navigate(ROUTES.ERROR);
          return;
        }
      }
    })();
  }, []);

  return (
    <S.Container>
      <Filter onFilterStatus={handleFilterStatus}>
        <option value="ALL">전체</option>
        <option value="BEFORE_APPROVED">승인전</option>
        <option value="APPROVED">승인완료</option>
        <option value="IN_PROGRESS">진행중</option>
        <option value="DONE">면담완료</option>
        <option value="CANCELED">면담취소</option>
      </Filter>
      <S.Table>
        <thead>
          <S.TheadRow>
            <td>진행 상태</td>
            <td>코치</td>
            <td>날짜</td>
            <td>시간</td>
            <td />
          </S.TheadRow>
        </thead>
        <tbody>
          {filteredHistory().map((history) => {
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
                onClickSheet={handleShowSheet(reservationId, status)}
                onClickCancel={handleCancelReservation}
              />
            );
          })}
        </tbody>
      </S.Table>
      {filteredHistory().length === 0 && <EmptyContent text={'현재 히스토리가 없습니다.'} />}
    </S.Container>
  );
};

export default CrewHistory;
