import { useContext, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import Board from '@components/Board';
import BoardItem from '@components/BoardItem';
import BoardSelectList from '@components/BoardSelectList';
import { UserDispatchContext } from '@context/UserProvider';
import useWindowFocus from '@hooks/useWindowFocus';
import useWindowSize from '@hooks/useWindowSize';
import useSelectList from '@hooks/useSelectList';
import { SnackbarContext } from '@context/SnackbarProvider';
import {
  confirmReservation,
  cancelReservation,
  rejectReservation,
  completeReservation,
} from '@api/reservation';
import { getCoachReservations } from '@api/coach';
import { getDateTime } from '@utils/date';
import { ROUTES } from '@constants/index';
import type { CrewListMap } from '@typings/domain';
import { theme, size } from '@styles/theme';
import * as S from './styles';

interface BoardItemValue {
  title: string;
  firstButton: string;
  secondButton: string;
  color: string;
  draggedColor: string;
  handleClickFirstButton: (
    index: number,
    status: string,
    reservationId: number,
    crewId: number
  ) => void;
  handleClickSecondButton: (
    index: number,
    status: string,
    reservationId: number,
    crewId: number
  ) => void;
}

interface BoardItem {
  [key: string]: BoardItemValue;
}

const CoachMain = () => {
  const navigate = useNavigate();
  const { width } = useWindowSize();
  const isWindowFocused = useWindowFocus();
  const { selectedItem: selectedBoard, handleSelectItem: handleSelectBoard } =
    useSelectList('beforeApproved');
  const showSnackbar = useContext(SnackbarContext);
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

  const handleApprove = async (index: number, status: string, reservationId: number) => {
    try {
      await confirmReservation(reservationId);
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

  const handleShowContents = (
    index: number,
    status: string,
    reservationId: number,
    crewId: number
  ) => {
    navigate(`${ROUTES.COACH_SHEET}/${reservationId}`, { state: { crewId } });
  };

  const handleCompleteReservation = async (
    index: number,
    status: string,
    reservationId: number
  ) => {
    try {
      await completeReservation(reservationId);
      showSnackbar({ message: '히스토리에 저장되었습니다. ✅' });
      deleteBoardItem(status, index);
    } catch (error) {
      if (error instanceof AxiosError) {
        alert(error.response?.data?.message);
        console.log(error);
      }
    }
  };

  const handleClickProfile = (crewId: number, crewName: string) => {
    navigate(`${ROUTES.HISTORY_SHEET}/${crewId}`, { state: crewName });
  };

  const handleReject = async (index: number, status: string, reservationId: number) => {
    if (!confirm('예약을 거절하시겠습니까?')) return;

    try {
      await rejectReservation(reservationId);
      deleteBoardItem(status, index);
      showSnackbar({ message: '거절되었습니다. ✅' });
    } catch (error) {
      if (error instanceof AxiosError) {
        alert(error.response?.data?.message);
        console.log(error);
      }
    }
  };

  const handleCancel = async (index: number, status: string, reservationId: number) => {
    if (!confirm('면담을 취소하시겠습니까?')) return;

    try {
      await cancelReservation(reservationId);
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

    if (from === to || from === 'inProgress' || to === 'beforeApproved') return;
    if (from === 'beforeApproved' && to === 'inProgress') return;
    if (to === 'inProgress' && getDateTime(draggedItem.dateTime) > new Date()) {
      showSnackbar({ message: '진행 가능한 시간이 아닙니다. 🚫' });
      return;
    }
    if (to === 'inProgress' && getDateTime(draggedItem.dateTime) < new Date()) {
      moveBoardItem(from, to, itemId);
      return;
    }

    handleApprove(itemId, '', draggedItem.reservationId);
  };

  const boardItem: BoardItem = {
    beforeApproved: {
      title: '대기중인 일정',
      firstButton: '거절하기',
      secondButton: '승인하기',
      color: theme.colors.ORANGE_600,
      draggedColor: theme.colors.ORANGE_100,
      handleClickFirstButton: handleReject,
      handleClickSecondButton: handleApprove,
    },
    approved: {
      title: '확정된 일정',
      firstButton: '취소하기',
      secondButton: '내용보기',
      color: theme.colors.PURPLE_300,
      draggedColor: theme.colors.PURPLE_100,
      handleClickFirstButton: handleCancel,
      handleClickSecondButton: handleShowContents,
    },
    inProgress: {
      title: '지난 일정',
      firstButton: '내용보기',
      secondButton: '완료하기',
      color: theme.colors.GREEN_700,
      draggedColor: theme.colors.GREEN_100,
      handleClickFirstButton: handleShowContents,
      handleClickSecondButton: handleCompleteReservation,
    },
  };

  useEffect(() => {
    const getReservationList = async () => {
      try {
        const { data: crewListMap } = await getCoachReservations();
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
      <BoardSelectList
        lists={[
          { id: 'beforeApproved', text: '대기중인 일정' },
          { id: 'approved', text: '확정된 일정' },
          { id: 'inProgress', text: '지난 일정' },
        ]}
        hidden={width > size.tablet}
        selectedItem={selectedBoard}
        onSelect={handleSelectBoard}
      />
      <S.BoardListContainer>
        {Object.keys(crews).map((status) => {
          const {
            title,
            firstButton,
            secondButton,
            color,
            draggedColor,
            handleClickFirstButton,
            handleClickSecondButton,
          } = boardItem[status];

          return (
            <Board
              isSelected={selectedBoard === status}
              status={status}
              key={status}
              title={title}
              color={color}
              length={crews[status].length}
              onDrop={handleDrop}
            >
              {crews[status].map((crew, index) => {
                const { crewId, reservationId, dateTime, crewImage, crewName, sheetStatus } = crew;
                return (
                  <BoardItem
                    key={reservationId}
                    dateTime={dateTime}
                    image={crewImage}
                    personName={crewName}
                    firstButton={firstButton}
                    secondButton={secondButton}
                    isButtonDisabled={status === 'approved' && sheetStatus === 'WRITING'}
                    color={color}
                    draggedColor={draggedColor}
                    onClickProfile={() => handleClickProfile(crewId, crewName)}
                    onClickFirstButton={() =>
                      handleClickFirstButton(index, status, reservationId, crewId)
                    }
                    onClickSecondButton={() =>
                      handleClickSecondButton(index, status, reservationId, crewId)
                    }
                    onDragStart={(e) => handleDragStart(e, index)}
                  />
                );
              })}
            </Board>
          );
        })}
      </S.BoardListContainer>
    </S.Layout>
  );
};

export default CoachMain;
