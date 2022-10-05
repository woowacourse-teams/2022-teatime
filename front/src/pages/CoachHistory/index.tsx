import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import TableRow from '@components/TableRow';
import EmptyContent from '@components/EmptyContent';
import Filter from '@components/Filter';
import { ROUTES } from '@constants/index';
import { getCoachHistories } from '@api/coach';
import type { CoachHistory as CoachHistoryType, CoachHistoryStatus } from '@typings/domain';
import { theme } from '@styles/theme';
import * as S from '../CrewHistory/styles';

type StatusValue = { statusName: string; color: string; backgroundColor: string };

type HistoryStatus = {
  [key in CoachHistoryStatus]: StatusValue;
};

const historyStatus: HistoryStatus = {
  CANCELED: {
    statusName: '면담취소',
    color: theme.colors.RED_500,
    backgroundColor: theme.colors.RED_100,
  },
  DONE: {
    statusName: '면담완료',
    color: theme.colors.GRAY_500,
    backgroundColor: theme.colors.GRAY_200,
  },
};

const CoachHistory = () => {
  const navigate = useNavigate();
  const [historyList, setHistoryList] = useState<CoachHistoryType[]>([]);
  const [category, setCategory] = useState('ALL');

  const handleShowSheet = (reservationId: number, crewId: number) => () => {
    navigate(`${ROUTES.COACH_SHEET}/${reservationId}`, { state: { crewId } });
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
        const { data } = await getCoachHistories();
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
    <S.Container>
      <Filter onFilterStatus={handleFilterStatus}>
        <option value="ALL">전체</option>
        <option value="DONE">면담완료</option>
        <option value="CANCELED">면담취소</option>
      </Filter>
      <S.Table>
        <thead>
          <S.TheadRow>
            <td>진행 상태</td>
            <td>크루</td>
            <td>날짜</td>
            <td>시간</td>
            <td />
          </S.TheadRow>
        </thead>
        <tbody>
          {filteredHistory().map((history) => {
            const { reservationId, crewId, status, crewName, crewImage, dateTime } = history;
            const { statusName, color, backgroundColor } = historyStatus[history.status];
            return (
              <TableRow
                key={history.reservationId}
                id={reservationId}
                status={status}
                isHiddenSheet={status === 'CANCELED'}
                name={crewName}
                image={crewImage}
                dateTime={dateTime}
                statusName={statusName}
                color={color}
                bgColor={backgroundColor}
                onClickSheet={handleShowSheet(reservationId, crewId)}
              />
            );
          })}
        </tbody>
      </S.Table>
      {filteredHistory().length === 0 && <EmptyContent text={'현재 히스토리가 없습니다.'} />}
    </S.Container>
  );
};

export default CoachHistory;
