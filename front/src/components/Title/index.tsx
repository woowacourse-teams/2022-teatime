import * as S from './styles';

interface TitleProps {
  text: string;
}

const Title = ({ text }: TitleProps) => {
  return (
    <S.TitleWrapper>
      <h1>{text}</h1>
    </S.TitleWrapper>
  );
};

export default Title;
