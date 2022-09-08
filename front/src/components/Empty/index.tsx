import * as S from './styles';
import EmptyImage from '@assets/empty.svg';

interface EmptyProps {
  text: string;
}

const Empty = ({ text }: EmptyProps) => {
  return (
    <S.Container>
      <img src={EmptyImage} draggable={false} />
      <span>{text}</span>
    </S.Container>
  );
};

export default Empty;
