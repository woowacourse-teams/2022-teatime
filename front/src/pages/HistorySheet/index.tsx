import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Frame from '@components/Frame';
import Sheet from '@components/Sheet';
import BackButton from '@components/BackButton';
import HistoryItem from '@components/HistoryItem';
import { HistoryList } from '@typings/domain';
import api from '@api/index';
import { LOCAL_DB, ROUTES } from '@constants/index';
import { getStorage } from '@utils/localStorage';
import * as S from '@styles/common';

const HistorySheet = () => {
  const navigate = useNavigate();
  const { token } = getStorage(LOCAL_DB.USER);
  const { id: crewId } = useParams();
  const [historyIndex, setHistoryIndex] = useState(0);
  const [historyList, setHistoryList] = useState<HistoryList[]>();

  const handleClickHistoryItem = (index: number) => {
    setHistoryIndex(index);
  };

  useEffect(() => {
    (async () => {
      try {
        const { data } = await api.get(`/api/v2/crews/${crewId}/reservations`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setHistoryList(data);
      } catch (error) {
        console.log(error);
      }
    })();
  }, []);

  if (!historyList) return <></>;

  if (!historyList.length) {
    alert('아직 히스토리가 없습니다.');
    navigate(ROUTES.COACH);
    return;
  }

  return (
    <Frame>
      <S.InfoContainer>
        {historyList.map((history, index) => {
          const { reservationId, coachImage, coachName, dateTime } = history;
          return (
            <HistoryItem
              key={reservationId}
              index={index}
              image={coachImage}
              name={coachName}
              dateTime={dateTime}
              onClick={handleClickHistoryItem}
              historyIndex={historyIndex}
            />
          );
        })}
        <BackButton />
      </S.InfoContainer>
      <Sheet title="작성한 면담 내용" sheets={historyList[historyIndex].sheets} isView />
    </Frame>
  );
};

export default HistorySheet;
