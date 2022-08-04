import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';

import Board from '@components/Board';
import BoardItem from '@components/BoardItem';
import api from '@api/index';
import theme from '@styles/theme';
import type { CrewListMap } from '@typings/domain';

import PlusIcon from '@assets/plus.svg';
import * as S from './styles';

interface BoardItemValue {
  title: string;
  buttonName: string;
  color: string;
  handleClickButton: (index: number, id: number) => void;
}

interface BoardItem {
  [key: string]: BoardItemValue;
}

const Coach = () => {
  const { id: coachId } = useParams();
  const navigate = useNavigate();
  const [crews, setCrews] = useState<CrewListMap>({
    beforeApproved: [],
    approved: [],
    inProgress: [],
  });

  const handleApprove = async (index: number, reservationId: number) => {
    try {
      await api.post(`/api/reservations/${reservationId}`, {
        coachId: 41,
        isApproved: true,
      });

      setCrews((allBoards) => {
        const copyBeforeApprovedBoard = [...allBoards.beforeApproved];
        const currentItem = copyBeforeApprovedBoard[index];
        const copyApprovedBoard = [...allBoards.approved];
        copyBeforeApprovedBoard.splice(index, 1);
        copyApprovedBoard.splice(index, 0, currentItem);

        return {
          ...allBoards,
          beforeApproved: copyBeforeApprovedBoard,
          approved: copyApprovedBoard,
        };
      });
    } catch (error) {
      alert('승인 에러');
      console.log(error);
    }
  };

  const boardItem: BoardItem = {
    beforeApproved: {
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
    inProgress: {
      title: '완료된 일정',
      buttonName: '이력작성',
      color: theme.colors.GREEN_700,
      handleClickButton: () => console.log('이력작성'),
    },
  };

  useEffect(() => {
    (async () => {
      try {
        const { data: crewListMap } = await api.get('/api/coaches/me/reservations', {
          headers: { coachId: String(coachId) },
        });

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
            <Board key={status} title={title} color={color} length={crews[status].length}>
              {crews[status].map((crew, index) => (
                <BoardItem
                  key={crew.reservationId}
                  dateTime={crew.dateTime}
                  image={crew.crewImage}
                  personName={crew.crewName}
                  buttonName={buttonName}
                  color={color}
                  onClick={() => handleClickButton(index, crew.reservationId)}
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
