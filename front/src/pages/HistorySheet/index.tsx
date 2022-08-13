import { useState } from 'react';
import { useParams } from 'react-router-dom';

import Frame from '@components/Frame';
import Sheet from '@components/Sheet';
import useFetch from '@hooks/useFetch';
import { HistoryList } from '@typings/domain';
import * as S from '@styles/common';
import HistoryItem from '@components/HistoryItem';

const HistorySheet = () => {
  const { id: crewId } = useParams();
  const [historyIndex, setHistoryIndex] = useState(0);
  const { data: historyList } = useFetch<HistoryList[], null>(`/api/crews/${crewId}/reservations`);

  const handleClickHistoryItem = (index: number) => {
    setHistoryIndex(index);
  };

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
      </S.InfoContainer>
      <Sheet title="작성한 면담 내용" sheets={historyList[historyIndex].sheets} isView />
    </Frame>
  );
};

export default HistorySheet;
