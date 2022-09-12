import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { AxiosError } from 'axios';

import Frame from '@components/Frame';
import Sheet from '@components/Sheet';
import BackButton from '@components/BackButton';
import HistoryItem from '@components/HistoryItem';
import { getCrewHistoriesByCoach } from '@api/crew';
import { ROUTES } from '@constants/index';
import { HistoryList } from '@typings/domain';
import * as S from '@styles/common';

const HistorySheet = () => {
  const navigate = useNavigate();
  const { id: crewId } = useParams();
  const [historyIndex, setHistoryIndex] = useState(0);
  const [historyList, setHistoryList] = useState<HistoryList[]>();

  const handleClickHistoryItem = (index: number) => {
    setHistoryIndex(index);
  };

  useEffect(() => {
    (async () => {
      try {
        const { data } = await getCrewHistoriesByCoach(crewId as string);
        setHistoryList(data);
      } catch (error) {
        if (error instanceof AxiosError) {
          alert(error.response?.data?.message);
          console.log(error);
        }
      }
    })();
  }, []);

  if (!historyList) return <></>;

  if (!historyList.length) {
    alert('아직 히스토리가 없습니다.');
    navigate(ROUTES.COACH);
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
      </S.InfoContainer>
      <Sheet title="작성한 면담 내용" sheets={historyList[historyIndex].sheets} isView />
      <BackButton />
    </Frame>
  );
};

export default HistorySheet;
