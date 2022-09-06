import { useContext, useEffect, useState } from 'react';

import TableRow from '@components/TableRow';
import { UserStateContext } from '@context/UserProvider';
import api from '@api/index';
import { HistoryCoach } from '@typings/domain';
import theme from '@styles/theme';
import * as S from '../CrewHistory/styles';

type StatusValue = { statusName: string; color: string; backgroundColor: string };

interface HistoryStatus {
  [key: string]: StatusValue;
}

const historyStatus: HistoryStatus = {
  CANCELED: {
    statusName: '면담취소',
    color: theme.colors.RED_400,
    backgroundColor: theme.colors.RED_100,
  },
  DONE: {
    statusName: '면담완료',
    color: theme.colors.GRAY_500,
    backgroundColor: theme.colors.GRAY_200,
  },
};

const CoachHistory = () => {
  const { userData } = useContext(UserStateContext);
  const [historyList, setHistoryList] = useState<HistoryCoach[]>([]);

  useEffect(() => {
    (async () => {
      try {
        const { data } = await api.get('/api/v2/coachs/me/history', {
          headers: {
            Authorization: `Bearer ${userData?.token}`,
          },
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
