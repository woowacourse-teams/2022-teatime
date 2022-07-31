import * as S from './styles';

interface BoardProps {
  children: React.ReactNode;
  title: string;
  color: string;
}

const Board = ({ children, title, color }: BoardProps) => {
  return (
    <S.BoardContainer>
      <S.TitleContainer color={color}>
        <div />
        <span>{title}</span>
      </S.TitleContainer>
      <S.ScrollContainer>{children}</S.ScrollContainer>
    </S.BoardContainer>
  );
};

export default Board;
