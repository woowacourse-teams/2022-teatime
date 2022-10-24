import { useEffect, useState } from 'react';
import { useParams, useLocation, useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import Frame from '@components/Frame';
import Sheet from '@components/Sheet';
import BackButton from '@components/BackButton';
import HistoryItem from '@components/HistoryItem';
import EmptyContent from '@components/EmptyContent';
import { getCrewHistoriesByCoach } from '@api/crew';
import type { HistoryList } from '@typings/domain';
import { logError } from '@utils/logError';
import { ROUTES } from '@constants/index';
import * as S from '@styles/common';

const HistorySheet = () => {
  const navigate = useNavigate();
  const { id: crewId } = useParams();
  const { state: crewName } = useLocation();
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
          logError(error);
          navigate(ROUTES.ERROR);
          return;
        }
      }
    })();
  }, []);

  return (
    <Frame>
      {!historyList?.length ? (
        <EmptyContent text={`현재 히스토리가 없습니다.`} />
      ) : (
        <>
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
          <Sheet
            title={`${crewName}의 면담 내용`}
            sheets={historyList[historyIndex].sheets}
            isReadOnly
          />
        </>
      )}
      <BackButton />
    </Frame>
  );
};

export default HistorySheet;
