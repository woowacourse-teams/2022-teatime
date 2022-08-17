import { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import Frame from '@components/Frame';
import Sheet from '@components/Sheet';
import BackButton from '@components/BackButton';
import HistoryItem from '@components/HistoryItem';
import { UserStateContext } from '@context/UserProvider';
import { HistoryList } from '@typings/domain';
import api from '@api/index';
import * as S from '@styles/common';

const HistorySheet = () => {
  const { id: crewId } = useParams();
  const { userData } = useContext(UserStateContext);
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
            Authorization: `Bearer ${userData?.token}`,
          },
        });
        setHistoryList(data);
      } catch (error) {
        console.log(error);
      }
    })();
  }, []);

  if (!historyList) return <></>;

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
