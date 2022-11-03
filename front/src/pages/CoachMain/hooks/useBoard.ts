import { useState } from 'react';
import type { BoardName, CrewListMap } from '@typings/domain';

const useBoard = () => {
  const [board, setBoard] = useState<CrewListMap>({
    beforeApproved: [],
    approved: [],
    inProgress: [],
  });

  const deleteBoardItem = (status: BoardName, index: number) => {
    setBoard((allBoards) => {
      const copyBoard = [...allBoards[status]];
      copyBoard.splice(index, 1);

      return {
        ...allBoards,
        [status]: copyBoard,
      };
    });
  };

  const moveBoardItem = (from: BoardName, to: BoardName, index: number) => {
    setBoard((allBoards) => {
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

  const sortBoardItemByTime = (boardName: BoardName) => {
    setBoard((allBoards) => {
      const copyBoard = [...allBoards[boardName]];
      copyBoard.sort((a, b) => Number(new Date(a.dateTime)) - Number(new Date(b.dateTime)));

      return {
        ...allBoards,
        [boardName]: copyBoard,
      };
    });
  };

  return { board, setBoard, deleteBoardItem, moveBoardItem, sortBoardItemByTime };
};

export default useBoard;
