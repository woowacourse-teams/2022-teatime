import * as S from './styles';

interface TextareaProps {
  id: string;
  label?: string;
  value: string;
  handleChangeContent: (e: React.ChangeEvent<HTMLTextAreaElement>, id: number) => void;
  isSubmit: boolean;
  isView: boolean;
}

const Textarea = ({ id, label, value, handleChangeContent, isSubmit, isView }: TextareaProps) => {
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
      <S.Label htmlFor={id}>{label}</S.Label>
      <S.Textarea
        id={id}
        value={value}
        onChange={onChangeContent}
        isFocus={isSubmit && !value}
        isView={isView}
        disabled={isView}
      />
      {isSubmit && !value && <S.Span>내용을 입력해 주세요.</S.Span>}
    </S.TextareaContainer>
  );
};

export default Textarea;
