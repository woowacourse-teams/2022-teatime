import { useEffect, useState } from 'react';
import { AxiosError } from 'axios';

import TableRow from '@components/TableRow';
import api from '@api/index';
import { CoachHistory as CoachHistoryType } from '@typings/domain';
import theme from '@styles/theme';
import * as S from '../CrewHistory/styles';

type StatusValue = { statusName: string; color: string; backgroundColor: string };

interface HistoryStatus {
  [key: string]: StatusValue;
}

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
  const [historyList, setHistoryList] = useState<CoachHistoryType[]>([]);

  useEffect(() => {
    (async () => {
      try {
        const { data } = await api.get('/api/v2/coaches/me/history');
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
          <td>크루</td>
          <td>날짜</td>
          <td>시간</td>
          <td />
        </tr>
      </S.Thead>
      <S.Tbody>
        {historyList.map((history) => {
          const { reservationId, status, crewName, crewImage, dateTime } = history;
          const { statusName, color, backgroundColor } = historyStatus[history.status];
          return (
            <TableRow
              key={history.reservationId}
              id={reservationId}
              status={status}
              name={crewName}
              image={crewImage}
              dateTime={dateTime}
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

export default CoachHistory;
