import * as S from './styles';

interface BoardProps {
  children: React.ReactNode;
  title: string;
  color: string;
  length: number;
  status: string;
  onDrop: (e: React.DragEvent<HTMLDivElement>) => void;
}

const Board = ({ children, title, color, length, onDrop, status }: BoardProps) => {
  const handleDragOver = (e: React.DragEvent<HTMLDivElement>) => {
    e.preventDefault();
  };

  return (
    <S.BoardContainer onDragOver={handleDragOver} onDrop={onDrop} data-status={status}>
      <S.TitleContainer color={color}>
        <S.TitleCircle />
        <span>{title}</span>
        <div>{length}</div>
      </S.TitleContainer>
      <S.ScrollContainer>{children}</S.ScrollContainer>
    </S.BoardContainer>
  );
};

export default Board;
