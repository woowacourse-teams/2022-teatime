import Tooltip from '@components/Tooltip';
import * as S from './styles';

interface TitleProps {
  text: string;
  highlightText?: string;
  hightlightColor?: string;
  extraText?: string;
  tooltipText?: string;
}

const Title = ({ text, highlightText, extraText, hightlightColor, tooltipText }: TitleProps) => {
  return (
    <S.TitleWrapper>
      <S.Title>{text}</S.Title>
      {highlightText && (
        <S.HighLightText hightlightColor={hightlightColor}>{highlightText}</S.HighLightText>
      )}
      {extraText && <S.Title>{extraText}</S.Title>}
      {tooltipText && <Tooltip text={tooltipText} />}
    </S.TitleWrapper>
  );
};

export default Title;
