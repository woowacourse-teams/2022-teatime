import * as S from './styles';

interface TextareaProps {
  id: string;
  label: string;
  value: string;
  handleChangeContent: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
  validation: (value: string) => boolean;
  isSubmit: boolean;
}

const Textarea = ({
  id,
  label,
  value,
  handleChangeContent,
  validation,
  isSubmit,
}: TextareaProps) => {
  return (
    <S.TextareaContainer>
      <S.Label htmlFor={id}>{label}</S.Label>
      <S.Textarea
        id={id}
        value={value}
        onChange={handleChangeContent}
        isFocus={isSubmit && validation(value)}
      />
      {isSubmit && validation(value) && <S.Span>내용을 입력해 주세요.</S.Span>}
    </S.TextareaContainer>
  );
};

export default Textarea;
