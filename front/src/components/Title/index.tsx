import HelpTip from '@components/HelpTip';
import * as S from './styles';

interface TitleProps {
  text: string;
  highlightText?: string;
  hightlightColor?: string;
  extraText?: string;
  helpTip?: string;
}

const Title = ({ text, highlightText, extraText, hightlightColor, helpTip }: TitleProps) => {
  return (
    <S.TitleWrapper>
      <S.Title>{text}</S.Title>
      {highlightText && (
        <S.HighLightText hightlightColor={hightlightColor}>{highlightText}</S.HighLightText>
      )}
      {extraText && <S.Title>{extraText}</S.Title>}
      {helpTip && <HelpTip text={helpTip} />}
    </S.TitleWrapper>
  );
};

export default Title;
