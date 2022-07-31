import * as S from './styles';

interface BoardProps {
  children: React.ReactNode;
  title: string;
  color: string;
  length: number;
}

const Board = ({ children, title, color, length }: BoardProps) => {
  return (
    <S.BoardContainer>
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
