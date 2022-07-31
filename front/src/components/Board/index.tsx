import * as S from './styles';

interface BoardProps {
  children: React.ReactNode;
  title: string;
}

const Board = ({ children, title }: BoardProps) => {
  return (
    <S.BoardContainer>
      <S.TitleContainer>
        <div />
        <span>{title}</span>
      </S.TitleContainer>
      <S.ScrollContainer>{children}</S.ScrollContainer>
    </S.BoardContainer>
  );
};

export default Board;
