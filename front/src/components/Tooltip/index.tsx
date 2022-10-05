import * as S from './styles';

const Tooltip = ({ text }: { text: string }) => {
  return (
    <S.TooltipBox>
      <p>{text}</p>
    </S.TooltipBox>
  );
};

export default Tooltip;
