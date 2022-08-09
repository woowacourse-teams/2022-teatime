import { useEffect, useState } from 'react';

import api from '@api/index';
import TableRow from '@components/TableRow';
import { History } from '@typings/domain';
import * as S from './styles';

const CrewHistory = () => {
  const [historyList, setHistoryList] = useState<History[]>([]);

  useEffect(() => {
    (async () => {
      try {
        const { data } = await api.get('/api/crews/me/reservations', {
          headers: { crewId: 17 },
        });
        console.log('data', data);
        setHistoryList(data);
      } catch (error) {
        alert('크루 히스토리 get 에러');
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
          return <TableRow key={history.reservationId} history={history} />;
        })}
      </S.Tbody>
    </S.Table>
  );
};

export default CrewHistory;
