import * as S from './styles';

const HelpTip = ({ text }: { text: string }) => {
  return (
    <S.Help>
      <p>{text}</p>
    </S.Help>
  );
};

export default HelpTip;
