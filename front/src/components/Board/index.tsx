import Empty from '@components/Empty';
import { useState } from 'react';
import * as S from './styles';

interface BoardProps {
  children: React.ReactNode;
  title: string;
  color: string;
  length: number;
  status: string;
  onDrop: (e: React.DragEvent<HTMLDivElement>) => Promise<void>;
}

const Board = ({ children, title, color, length, status, onDrop }: BoardProps) => {
  const [isDraggingOver, setIsDraggingOver] = useState(false);

  const handleDragOver = (e: React.DragEvent<HTMLDivElement>) => {
    e.preventDefault();
    setIsDraggingOver(true);
  };

  const handleDragLeave = () => {
    setIsDraggingOver(false);
  };

  const handleDrop = (e: React.DragEvent<HTMLDivElement>) => {
    onDrop(e);
    setIsDraggingOver(false);
  };

  return (
    <S.BoardContainer
      onDragOver={handleDragOver}
      onDrop={handleDrop}
      onDragLeave={handleDragLeave}
      isDraggingOver={isDraggingOver}
      data-status={status}
    >
      <S.TitleContainer color={color}>
        <S.TitleCircle />
        <span>{title}</span>
        <div>{length}</div>
      </S.TitleContainer>
      <S.ScrollContainer>
        {!length && <Empty text={`아직 ${title}이 없습니다.`} />}
        {children}
      </S.ScrollContainer>
    </S.BoardContainer>
  );
};

export default Board;
