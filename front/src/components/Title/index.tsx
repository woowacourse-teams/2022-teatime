import * as S from './styles';

interface TitleProps {
  text: string;
  highlightText?: string;
  hightlightColor?: string;
  extraText?: string;
}

const Title = ({ text, highlightText, extraText, hightlightColor }: TitleProps) => {
  return (
    <S.TitleWrapper>
      <S.Title>{text}</S.Title>
      {highlightText && (
        <S.HighLightText hightlightColor={hightlightColor}>{highlightText}</S.HighLightText>
      )}
      {extraText && <S.Title>{extraText}</S.Title>}
    </S.TitleWrapper>
  );
};

export default Title;
