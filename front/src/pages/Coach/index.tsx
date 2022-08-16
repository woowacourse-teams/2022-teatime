import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

import Board from '@components/Board';
import BoardItem from '@components/BoardItem';
import useSnackbar from '@hooks/useSnackbar';
import type { CrewListMap } from '@typings/domain';
import { LOCAL_DB, ROUTES } from '@constants/index';
import { getStorage } from '@utils/localStorage';
import { getDateTime } from '@utils/index';
import api from '@api/index';

import ScheduleIcon from '@assets/schedule-white.svg';
import theme from '@styles/theme';
import * as S from './styles';

interface BoardItemValue {
  title: string;
  buttonName: string;
  color: string;
  draggedColor: string;
  handleClickMenuButton: (index: number, reservationId: number, crewId?: number) => void;
  handleClickCancelButton: (status: string, index: number, id: number) => void;
}

interface BoardItem {
  [key: string]: BoardItemValue;
}

const Coach = () => {
  const navigate = useNavigate();
  const showSnackBar = useSnackbar();
  const { token } = getStorage(LOCAL_DB.USER);
  const [crews, setCrews] = useState<CrewListMap>({
    beforeApproved: [],
    approved: [],
    inProgress: [],
  });

  const deleteBoardItem = (status: string, index: number) => {
    setCrews((allBoards) => {
      const copyBeforeStatusBoard = [...allBoards[status]];
      copyBeforeStatusBoard.splice(index, 1);

      return {
        ...allBoards,
        [status]: copyBeforeStatusBoard,
      };
    });
  };

  const moveBoardItem = (from: string, to: string, index: number) => {
    setCrews((allBoards) => {
      const copyFromBoard = [...allBoards[from]];
      const currentItem = copyFromBoard[index];
      const copyToBoard = [...allBoards[to]];
      copyFromBoard.splice(index, 1);
      copyToBoard.push(currentItem);

      return {
        ...allBoards,
        [from]: copyFromBoard,
        [to]: copyToBoard,
      };
    });
  };

  const sortBoardItemByTime = (boardName: string) => {
    setCrews((allBoards) => {
      const copyBoard = [...allBoards[boardName]];
      copyBoard.sort((a, b) => Number(new Date(a.dateTime)) - Number(new Date(b.dateTime)));

      return {
        ...allBoards,
        [boardName]: copyBoard,
      };
    });
  };

  const handleApprove = async (index: number, reservationId: number) => {
    try {
      await api.post(
        `/api/v2/reservations/${reservationId}`,
        {
          isApproved: true,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      moveBoardItem('beforeApproved', 'approved', index);
      sortBoardItemByTime('approved');
    } catch (error) {
      alert('승인 에러');
      console.log(error);
    }
  };

  const handleShowContents = (index: number, reservationId: number, crewId?: number) => {
    navigate(`${ROUTES.COACH_SHEET}/${reservationId}`, { state: crewId });
  };

  const handleReject = async (status: string, index: number, reservationId: number) => {
    if (!confirm('예약을 거절하시겠습니까?')) return;

    try {
      await api.post(
        `/api/v2/reservations/${reservationId}`,
        {
          isApproved: false,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      deleteBoardItem(status, index);
      showSnackBar({ message: '삭제되었습니다. ✅' });
    } catch (error) {
      alert('거절 기능 에러');
      console.log(error);
    }
  };

  const handleCancel = async (status: string, index: number, reservationId: number) => {
    if (!confirm(status === 'approved' ? '예약을 취소하시겠습니까?' : '정말 삭제하시겠습니까?'))
      return;

    try {
      await api.delete(`/api/v2/reservations/${reservationId}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      deleteBoardItem(status, index);
    } catch (error) {
      alert('취소 에러');
      console.log(error);
    }
  };

  const handleDragStart = (e: React.DragEvent<HTMLDivElement>, id: number) => {
    e.dataTransfer?.setData('itemId', String(id));
    e.dataTransfer?.setData(
      'listId',
      ((e.target as Element).closest('[data-status]') as HTMLElement).dataset.status as string
    );
  };

  const handleDrop = async (e: React.DragEvent<HTMLDivElement>) => {
    const itemId = Number(e.dataTransfer?.getData('itemId'));
    const from = e.dataTransfer?.getData('listId');
    const to = ((e.target as Element).closest('[data-status]') as HTMLElement).dataset
      .status as string;
    const draggedItem = crews[from][itemId];

    if (from === to) return;
    if (from === 'inProgress' || to === 'beforeApproved') return;
    if (from === 'beforeApproved' && to === 'inProgress') return;
    if (to === 'inProgress' && getDateTime(draggedItem.dateTime) > new Date()) {
      alert('아직 옮길 수 없어요.');
      return;
    }
    if (to === 'inProgress' && getDateTime(draggedItem.dateTime) < new Date()) {
      moveBoardItem(from, to, itemId);
      return;
    }

    handleApprove(itemId, draggedItem.reservationId);
  };

  const boardItem: BoardItem = {
    beforeApproved: {
      title: '대기중인 일정',
      buttonName: '승인하기',
      color: theme.colors.ORANGE_600,
      draggedColor: theme.colors.ORANGE_100,
      handleClickMenuButton: handleApprove,
      handleClickCancelButton: handleReject,
    },
    approved: {
      title: '확정된 일정',
      buttonName: '내용보기',
      color: theme.colors.PURPLE_300,
      draggedColor: theme.colors.PURPLE_100,
      handleClickMenuButton: handleShowContents,
      handleClickCancelButton: handleCancel,
    },
    inProgress: {
      title: '진행중인 일정',
      buttonName: '이력작성',
      color: theme.colors.GREEN_700,
      draggedColor: theme.colors.GREEN_100,
      handleClickMenuButton: () => console.log('이력작성'),
      handleClickCancelButton: handleCancel,
    },
  };

  useEffect(() => {
    (async () => {
      try {
        const { data: crewListMap } = await api.get('/api/v2/coaches/me/reservations', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
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
          <img src={ScheduleIcon} alt="일정 아이콘" />
          <span>캘린더 관리</span>
        </S.AddScheduleButton>
      </S.BoardListHeader>
      <S.BoardListContainer>
        {Object.keys(crews).map((status) => {
          const {
            title,
            buttonName,
            color,
            draggedColor,
            handleClickMenuButton,
            handleClickCancelButton,
          } = boardItem[status];

          return (
            <Board
              status={status}
              key={status}
              title={title}
              color={color}
              length={crews[status].length}
              onDrop={handleDrop}
            >
              {crews[status].map((crew, index) => (
                <BoardItem
                  key={crew.reservationId}
                  dateTime={crew.dateTime}
                  image={crew.crewImage}
                  personName={crew.crewName}
                  buttonName={buttonName}
                  color={color}
                  draggedColor={draggedColor}
                  onClickMenu={() => handleClickMenuButton(index, crew.reservationId, crew.crewId)}
                  onClickCancel={() => handleClickCancelButton(status, index, crew.reservationId)}
                  onDragStart={(e) => handleDragStart(e, index)}
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
