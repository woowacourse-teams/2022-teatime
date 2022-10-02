import { useState } from 'react';

import Textarea from '@components/Textarea';
import HelpTip from '@components/HelpTip';
import { Sheets } from '@typings/domain';
import { MAX_LENGTH } from '@constants/index';
import * as S from './styles';

interface SheetProps {
  title: string;
  sheets: Sheets[];
  onSubmit?: (isSubmitted: boolean, contents: Sheets[]) => void;
  isReadOnly: boolean;
}

const Sheet = ({ title, sheets, onSubmit, isReadOnly }: SheetProps) => {
  const [isSubmit, setIsSubmit] = useState(false);
  const [contents, setContents] = useState<Sheets[]>(sheets);

  const handleChangeContent = (e: React.ChangeEvent<HTMLTextAreaElement>, index: number) => {
    if (e.target.value.length > MAX_LENGTH.SHEET) {
      alert('더 이상 작성할 수 없습니다.');
      e.target.value = e.target.value.substring(0, MAX_LENGTH.SHEET);
    }
    setContents((prevContent) => {
      const newContent = [...prevContent];
      newContent[index].answerContent = e.target.value;
      return newContent;
    });
  };

  const handleSubmitButton = async (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();

    const isSubmitted = e.currentTarget.innerText === '제출하기';
    if (isSubmitted) {
      setIsSubmit(true);
      const checkValidation = contents.some((content) => !content.answerContent);
      if (checkValidation) return;

      if (!confirm('⛔️제출 시 수정은 불가합니다.⛔️\n\n 정말로 제출하시겠습니까?')) return;
    }
    onSubmit?.(isSubmitted, contents);
  };

  return (
    <>
      <S.SheetContainer>
        <S.TitleWrapper>
          <h2>{title}</h2>
          {!isReadOnly && <HelpTip text="임시저장된 내용은 상대방에게 보이지 않습니다." />}
        </S.TitleWrapper>
        <form>
          <Textarea
            id="0"
            label={contents[0].questionContent}
            value={sheets[0].answerContent || ''}
            handleChangeContent={handleChangeContent}
            isSubmit={isSubmit}
            isReadOnly={isReadOnly}
          />
          <Textarea
            id="1"
            label={contents[1].questionContent}
            value={sheets[1].answerContent || ''}
            handleChangeContent={handleChangeContent}
            isSubmit={isSubmit}
            isReadOnly={isReadOnly}
          />
          <Textarea
            id="2"
            label={contents[2].questionContent}
            value={sheets[2].answerContent || ''}
            handleChangeContent={handleChangeContent}
            isSubmit={isSubmit}
            isReadOnly={isReadOnly}
          />
          {!isReadOnly && (
            <S.ButtonContainer>
              <S.FirstButton onClick={handleSubmitButton}>임시저장</S.FirstButton>
              <S.SecondButton onClick={handleSubmitButton}>제출하기</S.SecondButton>
            </S.ButtonContainer>
          )}
        </form>
      </S.SheetContainer>
    </>
  );
};

export default Sheet;
