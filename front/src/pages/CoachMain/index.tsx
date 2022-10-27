import { useContext, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

import Board from '@components/Board';
import BoardItem from '@components/BoardItem';
import BoardSelectList from '@components/BoardSelectList';
import useWindowFocus from '@hooks/useWindowFocus';
import useWindowSize from '@hooks/useWindowSize';
import useBoard from './hooks/useBoard';
import { SnackbarContext } from '@context/SnackbarProvider';
import { BoardStateContext } from '@context/BoardModeProvider';
import {
  confirmReservation,
  cancelReservation,
  rejectReservation,
  completeReservation,
} from '@api/reservation';
import { getCoachReservations } from '@api/coach';
import { getDateTime } from '@utils/date';
import { BOARD, ERROR_MESSAGE, ROUTES } from '@constants/index';
import type { BoardName } from '@typings/domain';
import { logError } from '@utils/logError';
import { theme, size } from '@styles/theme';
import * as S from './styles';

type ClickBoardButton = (
  index: number,
  status: BoardName,
  reservationId: number,
  crewId: number
) => void;

type BoardItemValue = {
  title: string;
  firstButton: string;
  secondButton: string;
  color: string;
  draggedColor: string;
  handleClickFirstButton: ClickBoardButton;
  handleClickSecondButton: ClickBoardButton;
};

type BoardItem = {
  [key in BoardName]: BoardItemValue;
};

const CoachMain = () => {
  const navigate = useNavigate();
  const { width } = useWindowSize();
  const isWindowFocused = useWindowFocus();
  const { board, setBoard, deleteBoardItem, moveBoardItem, sortBoardItemByTime } = useBoard();
  const selectedBoard = useContext(BoardStateContext);
  const showSnackbar = useContext(SnackbarContext);

  const handleApprove = async (index: number, status: string, reservationId: number) => {
    try {
      await confirmReservation(reservationId);
      moveBoardItem('beforeApproved', 'approved', index);
      sortBoardItemByTime('approved');
      showSnackbar({ message: '승인되었습니다. ✅' });
    } catch (error) {
      if (error instanceof AxiosError) {
        logError(error);
        alert(ERROR_MESSAGE.FAIL_APPROVE);
        return;
      }
    }
  };

  const handleShowContents = (
    index: number,
    status: BoardName,
    reservationId: number,
    crewId: number
  ) => {
    navigate(`${ROUTES.COACH_SHEET}/${reservationId}`, { state: { crewId } });
  };

  const handleCompleteReservation = async (
    index: number,
    status: BoardName,
    reservationId: number
  ) => {
    try {
      await completeReservation(reservationId);
      deleteBoardItem(status, index);
      showSnackbar({ message: '히스토리에 저장되었습니다. ✅' });
    } catch (error) {
      if (error instanceof AxiosError) {
        logError(error);
        alert(ERROR_MESSAGE.FAIL_COMPLETE_RESERVATION);
        return;
      }
    }
  };

  const handleClickProfile = (crewId: number, crewName: string) => {
    navigate(`${ROUTES.HISTORY_SHEET}/${crewId}`, { state: crewName });
  };

  const handleReject = async (index: number, status: BoardName, reservationId: number) => {
    if (!confirm('예약을 거절하시겠습니까?')) return;

    try {
      await rejectReservation(reservationId);
      deleteBoardItem(status, index);
      showSnackbar({ message: '거절되었습니다. ✅' });
    } catch (error) {
      if (error instanceof AxiosError) {
        logError(error);
        alert(ERROR_MESSAGE.FAIL_REJECT_RESERVATION);
        return;
      }
    }
  };

  const handleCancel = async (index: number, status: BoardName, reservationId: number) => {
    if (!confirm('면담을 취소하시겠습니까?')) return;

    try {
      await cancelReservation(reservationId);
      deleteBoardItem(status, index);
      showSnackbar({ message: '취소되었습니다. ✅' });
    } catch (error) {
      if (error instanceof AxiosError) {
        logError(error);
        alert(ERROR_MESSAGE.FAIL_CANCEL_RESERVATION);
        return;
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
    const from = e.dataTransfer?.getData('listId') as BoardName;
    const to = ((e.target as Element).closest('[data-status]') as HTMLElement).dataset
      .status as BoardName;
    const draggedItem = board[from][itemId];

    if (
      from === to ||
      from === 'inProgress' ||
      to === 'beforeApproved' ||
      (from === 'beforeApproved' && to === 'inProgress')
    ) {
      return;
    }
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
        setBoard(crewListMap);
      } catch (error) {
        if (error instanceof AxiosError) {
          logError(error);
          navigate(ROUTES.ERROR);
          return;
        }
      }
    };

    isWindowFocused && getReservationList();
  }, [isWindowFocused]);

  return (
    <S.Layout>
      <BoardSelectList
        lists={[
          { id: BOARD.BEFORE_APPROVED, text: '대기중인 일정' },
          { id: BOARD.APPROVED, text: '확정된 일정' },
          { id: BOARD.IN_PROGRESS, text: '지난 일정' },
        ]}
        hidden={width > size.tablet}
        selectedItem={selectedBoard}
      />
      <S.BoardListContainer>
        {(Object.keys(board) as BoardName[]).map((status) => {
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
              length={board[status].length}
              onDrop={handleDrop}
            >
              {board[status].map((crew, index) => {
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
