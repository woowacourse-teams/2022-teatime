import { useState } from 'react';

import Textarea from '@components/Textarea';
import { Sheets } from '@typings/domain';
import { SHEET_MAX_LENGTH } from '@constants/index';
import * as S from './styles';

interface SheetProps {
  title: string;
  sheets: Sheets[];
  onSubmit?: (isSubmitted: boolean, contents: Sheets[]) => void;
  isUnalterable?: boolean;
  isView: boolean;
}

const Sheet = ({ title, sheets, onSubmit, isUnalterable, isView }: SheetProps) => {
  const [isSubmit, setIsSubmit] = useState(false);
  const [contents, setContents] = useState<Sheets[]>(sheets);

  const handleChangeContent = (e: React.ChangeEvent<HTMLTextAreaElement>, index: number) => {
    if (e.target.value.length > SHEET_MAX_LENGTH) {
      alert('더 이상 작성할 수 없습니다.');
      e.target.value = e.target.value.substring(0, SHEET_MAX_LENGTH);
    }
    setContents((prevContent) => {
      const newContent = [...prevContent];
      newContent[index].answerContent = e.target.value;
      return newContent;
    });
  };

  const handleSubmitButton = async (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();

    if (isUnalterable) return alert('면담이 진행되면 제출할 수 없습니다.🚫');

    const isSubmitted = e.currentTarget.innerText === '제출하기';
    if (isSubmitted) {
      setIsSubmit(true);
      const checkValidation = contents.some((content) => !content.answerContent);
      if (checkValidation) return;

      if (!confirm('📮 정말로 제출하시겠습니까?\n\n✔️ 제출 시 수정은 불가합니다.')) return;
    }
    onSubmit?.(isSubmitted, contents);
  };

  return (
    <>
      <S.SheetContainer>
        <h2>{title}</h2>
        <form>
          <Textarea
            id="0"
            label={contents[0].questionContent}
            value={sheets[0].answerContent || ''}
            handleChangeContent={handleChangeContent}
            isSubmit={isSubmit}
            isView={isView}
          />
          <Textarea
            id="1"
            label={contents[1].questionContent}
            value={sheets[1].answerContent || ''}
            handleChangeContent={handleChangeContent}
            isSubmit={isSubmit}
            isView={isView}
          />
          <Textarea
            id="2"
            label={contents[2].questionContent}
            value={sheets[2].answerContent || ''}
            handleChangeContent={handleChangeContent}
            isSubmit={isSubmit}
            isView={isView}
          />
          {!isView && (
            <S.ButtonContainer isUnalterable={!!isUnalterable}>
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
