import * as S from './styles';

interface TextareaProps {
  id: string;
  label: string;
  value: string;
  handleChangeContent: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
  isSubmit: boolean;
}

const Textarea = ({ id, label, value, handleChangeContent, isSubmit }: TextareaProps) => {
  return (
    <S.TextareaContainer>
      <S.Label htmlFor={id}>{label}</S.Label>
      <S.Textarea
        id={id}
        value={value}
        onChange={handleChangeContent}
        isFocus={isSubmit && !value}
      />
      {isSubmit && !value && <S.Span>내용을 입력해 주세요.</S.Span>}
    </S.TextareaContainer>
  );
};

export default Textarea;
