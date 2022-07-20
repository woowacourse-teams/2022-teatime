import { TitleWrapper } from './styles';

interface TitleProps {
  text: string;
}

const Title = ({ text }: TitleProps) => {
  return (
    <TitleWrapper>
      <h1>{text}</h1>
    </TitleWrapper>
  );
};

export default Title;
