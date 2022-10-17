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
    <S.TitleWrapper role="status" aria-label={`${text} ${highlightText} ${extraText}`}>
      <S.Title aria-hidden="true">{text}</S.Title>
      {highlightText && (
        <S.HighLightText aria-hidden="true" hightlightColor={hightlightColor}>
          {highlightText}
        </S.HighLightText>
      )}
      {extraText && <S.Title aria-hidden="true">{extraText}</S.Title>}
      {tooltipText && <Tooltip text={tooltipText} />}
    </S.TitleWrapper>
  );
};

export default Title;
