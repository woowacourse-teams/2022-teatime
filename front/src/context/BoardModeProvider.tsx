import { createContext, useState } from 'react';
import { BOARD } from '@constants/index';
import { BoardValue } from '@typings/domain';

type BoardContextType = (e: React.MouseEvent<HTMLElement>) => void;

export const BoardStateContext = createContext<BoardValue>(BOARD.BEFORE_APPROVED);
export const BoardChangeContext = createContext<BoardContextType>(() => null);

const BoardProvider = ({ children }: { children: React.ReactNode }) => {
  const [selectedBoard, setSelectedBoard] = useState<BoardValue>(BOARD.BEFORE_APPROVED);

  const changeBoard = (e: React.MouseEvent<HTMLElement>) => {
    const target = e.target as HTMLElement;
    if (target.tagName !== 'LI') return;
    setSelectedBoard(target.id as BoardValue);
  };

  return (
    <BoardStateContext.Provider value={selectedBoard}>
      <BoardChangeContext.Provider value={changeBoard}>{children}</BoardChangeContext.Provider>
    </BoardStateContext.Provider>
  );
};

export default BoardProvider;