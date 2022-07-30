import * as S from './styles';

interface BoardProps {
  children: React.ReactNode;
  boardTitle: string;
}

const Board = ({ children, boardTitle }: BoardProps) => {
  return (
    <S.BoardContainer>
      <S.TitleContainer>
        <div />
        <span>{boardTitle}</span>
      </S.TitleContainer>
      <S.ScrollContainer>{children}</S.ScrollContainer>
    </S.BoardContainer>
  );
};

export default Board;
