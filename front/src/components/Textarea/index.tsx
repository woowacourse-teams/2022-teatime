import * as S from './styles';

interface TextareaProps {
  id: string;
  label: string;
}

const Textarea = ({ id, label }: TextareaProps) => {
  return (
    <div>
      <S.Label htmlFor={id}>{label}</S.Label>
      <S.Textarea id={id}></S.Textarea>
    </div>
  );
};

export default Textarea;
