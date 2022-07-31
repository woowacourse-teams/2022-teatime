import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import api from '@api/index';
import Board from '@components/Board';
import BoardItem from '@components/BoardItem';
import Conditional from '@components/Conditional';
import type { CrewList, CrewListMap } from '@typings/domain';
import PlusIcon from '@assets/plus.svg';
import * as S from './styles';
import theme from '@styles/theme';

interface BoardItemValue {
  title: string;
  buttonName: string;
  color: string;
  handleClickButton: () => void;
}

interface BoardItem {
  [key: string]: BoardItemValue;
}

const Coach = () => {
  const navigate = useNavigate();
  const [crews, setCrews] = useState<CrewListMap>({
    pending: [],
    approved: [],
    completed: [],
  });

  const handleApprove = () => {
    console.log('승인되었습니다!');
  };

  const boardItem: BoardItem = {
    pending: {
      title: '대기중인 일정',
      buttonName: '승인하기',
      color: theme.colors.ORANGE_600,
      handleClickButton: handleApprove,
    },
    approved: {
      title: '확정된 일정',
      buttonName: '내용보기',
      color: theme.colors.PURPLE_300,
      handleClickButton: () => console.log('내용보기'),
    },
    completed: {
      title: '완료된 일정',
      buttonName: '이력작성',
      color: theme.colors.GREEN_700,
      handleClickButton: () => console.log('이력작성'),
    },
  };

  useEffect(() => {
    (async () => {
      try {
        const { data: crewList } = await api.get('/api/crews');
        const crewListMap = crewList?.reduce((newObj: CrewListMap, { status, crews }: CrewList) => {
          newObj[status] = crews;
          return newObj;
        }, {});

        setCrews(crewListMap);
      } catch (error) {
        alert('크루 목록 get 에러');
        console.log(error);
      }
    })();
  }, []);

  return (
    <S.Layout>
      <S.BoardListHeader>
        <S.AddScheduleButton onClick={() => navigate(`/schedule/41`)}>
          <img src={PlusIcon} alt="추가 아이콘" />
          <span>일정 추가</span>
        </S.AddScheduleButton>
      </S.BoardListHeader>
      <S.BoardListContainer>
        {Object.keys(crews).map((status) => {
          const { title, buttonName, color, handleClickButton } = boardItem[status];

          return (
            <Board key={status} title={title} color={color}>
              {crews[status].map((crew) => (
                <BoardItem
                  key={crew.id}
                  dateTime={crew.dateTime}
                  image={crew.image}
                  personName={crew.name}
                  buttonName={buttonName}
                  color={color}
                  onClick={handleClickButton}
                />
              ))}
            </Board>
          );
        })}
      </S.BoardListContainer>
    </S.Layout>
  );
};

export default Coach;
