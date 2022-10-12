import * as S from './styles';

interface TextareaProps {
  id: string;
  label?: string;
  value: string;
  handleChangeContent: (e: React.ChangeEvent<HTMLTextAreaElement>, id: number) => void;
  isSubmit: boolean;
  isReadOnly: boolean;
  isRequired?: boolean;
}

const Textarea = ({
  id,
  label,
  value,
  handleChangeContent,
  isSubmit,
  isReadOnly,
  isRequired,
}: TextareaProps) => {
  const onChangeContent = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const textarea = document.getElementById(id);
    if (textarea) {
      textarea.style.height = '86px';
      const height = textarea.scrollHeight;
      textarea.style.height = `${height + 8}px`;
    }
    handleChangeContent(e, Number(id));
  };

  return (
    <S.TextareaContainer>
      <S.Label htmlFor={id} isRequired={isRequired}>
        {label} <span>{isRequired ? '(필수)' : '(선택)'}</span>
      </S.Label>
      <S.Textarea
        id={id}
        value={value}
        onChange={onChangeContent}
        isFocus={isSubmit && !value && !!isRequired}
        isReadOnly={isReadOnly}
        disabled={isReadOnly}
      />
      {isSubmit && !value && isRequired && <S.Span>내용을 입력해 주세요.</S.Span>}
    </S.TextareaContainer>
  );
};

export default Textarea;
