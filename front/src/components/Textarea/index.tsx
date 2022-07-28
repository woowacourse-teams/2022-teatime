import * as S from './styles';

interface TextareaProps {
  id: string;
  label: string;
  value: string;
  handleChangeContent: (e: React.ChangeEvent<HTMLTextAreaElement>) => void;
}

const Textarea = ({ id, label, value, handleChangeContent }: TextareaProps) => {
  return (
    <S.TextareaContainer>
      <S.Label htmlFor={id}>{label}</S.Label>
      <S.Textarea id={id} value={value} onChange={handleChangeContent}></S.Textarea>
    </S.TextareaContainer>
  );
};

export default Textarea;
