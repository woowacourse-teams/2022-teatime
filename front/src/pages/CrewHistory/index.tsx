import { useEffect, useState } from 'react';

import api from '@api/index';
import TableRow from '@components/TableRow';
import { History } from '@typings/domain';
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
    statusName: '면담완료',
    color: theme.colors.GREEN_700,
    backgroundColor: theme.colors.GREEN_100,
  },
};

const CrewHistory = () => {
  const [historyList, setHistoryList] = useState<History[]>([]);

  useEffect(() => {
    (async () => {
      try {
        const { data } = await api.get('/api/crews/me/reservations', {
          headers: { crewId: 17 },
        });
        setHistoryList(data);
      } catch (error) {
        console.log(error);
      }
    })();
  }, []);

  return (
    <S.Table>
      <caption>마이 페이지</caption>
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
          const { statusName, color, backgroundColor } = historyStatus[history.status];
          return (
            <TableRow
              key={history.reservationId}
              history={history}
              statusName={statusName}
              color={color}
              bgColor={backgroundColor}
            />
          );
        })}
      </S.Tbody>
    </S.Table>
  );
};

export default CrewHistory;
