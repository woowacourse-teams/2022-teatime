import * as S from './styles';
import EmptyImage from '@assets/empty.svg';

interface EmptyContentProps {
  text: string;
}

const EmptyContent = ({ text }: EmptyContentProps) => {
  return (
    <S.Container>
      <img src={EmptyImage} draggable={false} alt="내용 없음" />
      <span>{text}</span>
    </S.Container>
  );
};

export default EmptyContent;
