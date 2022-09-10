import { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import Board from '@components/Board';
import BoardItem from '@components/BoardItem';
import { UserDispatchContext, UserStateContext } from '@context/UserProvider';
import useWindowFocus from '@hooks/useWindowFocus';
import { SnackbarContext } from '@context/SnackbarProvider';
import type { CrewListMap } from '@typings/domain';
import { ROUTES } from '@constants/index';
import { getDateTime } from '@utils/date';
import api from '@api/index';
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
  const isWindowFocused = useWindowFocus();
  const showSnackbar = useContext(SnackbarContext);
  const { userData } = useContext(UserStateContext);
  const dispatch = useContext(UserDispatchContext);
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
            Authorization: `Bearer ${userData?.token}`,
          },
        }
      );

      moveBoardItem('beforeApproved', 'approved', index);
      sortBoardItemByTime('approved');
      showSnackbar({ message: '승인되었습니다. ✅' });
    } catch (error) {
      if (error instanceof AxiosError) {
        alert(error.response?.data?.message);
        console.log(error);
      }
    }
  };

  const handleShowApprovedContents = (index: number, reservationId: number, crewId?: number) => {
    navigate(`${ROUTES.COACH_SHEET}/${reservationId}`, { state: { crewId } });
  };

  const handleShowInProgressContents = (index: number, reservationId: number, crewId?: number) => {
    navigate(`${ROUTES.COACH_SHEET}/${reservationId}`, {
      state: { crewId, hasCompleteButton: true },
    });
  };

  const handleClickProfile = (crewId: number) => {
    navigate(`${ROUTES.HISTORY_SHEET}/${crewId}`);
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
            Authorization: `Bearer ${userData?.token}`,
          },
        }
      );

      deleteBoardItem(status, index);
      showSnackbar({ message: '거절되었습니다. ✅' });
    } catch (error) {
      if (error instanceof AxiosError) {
        alert(error.response?.data?.message);
        console.log(error);
      }
    }
  };

  const handleCancel = async (status: string, index: number, reservationId: number) => {
    if (!confirm('면담을 취소하시겠습니까?')) return;

    try {
      await api.delete(`/api/v2/reservations/${reservationId}`, {
        headers: {
          Authorization: `Bearer ${userData?.token}`,
        },
      });

      deleteBoardItem(status, index);
      showSnackbar({ message: '취소되었습니다. ✅' });
    } catch (error) {
      if (error instanceof AxiosError) {
        alert(error.response?.data?.message);
        console.log(error);
      }
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
      showSnackbar({ message: '진행 가능한 시간이 아닙니다. 🚫' });
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
      handleClickMenuButton: handleShowApprovedContents,
      handleClickCancelButton: handleCancel,
    },
    inProgress: {
      title: '진행중인 일정',
      buttonName: '내용보기',
      color: theme.colors.GREEN_700,
      draggedColor: theme.colors.GREEN_100,
      handleClickMenuButton: handleShowInProgressContents,
      handleClickCancelButton: handleCancel,
    },
  };

  useEffect(() => {
    const getReservationList = async () => {
      try {
        const { data: crewListMap } = await api.get('/api/v2/coaches/me/reservations', {
          headers: {
            Authorization: `Bearer ${userData?.token}`,
          },
        });
        setCrews(crewListMap);
      } catch (error) {
        if (error instanceof AxiosError) {
          if (error.response?.status === 401) {
            dispatch({ type: 'DELETE_USER' });
            showSnackbar({ message: '토큰이 만료되었습니다. 다시 로그인해주세요.' });
            navigate(ROUTES.HOME);
            return;
          }
          alert(error);
        }
      }
    };

    isWindowFocused && getReservationList();
  }, [isWindowFocused]);

  return (
    <S.Layout>
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
                  isButtonDisabled={status === 'approved' && crew.sheetStatus === 'WRITING'}
                  color={color}
                  draggedColor={draggedColor}
                  onClickMenu={() => handleClickMenuButton(index, crew.reservationId, crew.crewId)}
                  onClickProfile={() => handleClickProfile(crew.crewId)}
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
