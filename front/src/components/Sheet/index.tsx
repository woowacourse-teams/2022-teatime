import { useState } from 'react';

import Textarea from '@components/Textarea';
import Title from '@components/Title';
import * as S from './styles';
import { Sheets } from '@typings/domain';

interface SheetProps {
  title: string;
  sheets: Sheets[];
  handleSubmit?: (isSubmitted: boolean, contents: Sheets[]) => void;
  isView: boolean;
}

const Sheet = ({ title, sheets, handleSubmit, isView }: SheetProps) => {
  const [isSubmit, setIsSubmit] = useState(false);
  const [contents, setContents] = useState<Sheets[]>(sheets);

  const handleChangeContent = (index: number) => (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContents((prevContent) => {
      const newContent = [...prevContent];
      newContent[index].answerContent = e.target.value;
      return newContent;
    });
  };

  const onSubmitButton = async (e: React.MouseEvent<HTMLButtonElement>) => {
    e.preventDefault();

    const isSubmitted = e.currentTarget.innerText === '제출하기';
    if (isSubmitted) {
      setIsSubmit(true);
      const checkValidation = contents.some((content) => !content.answerContent);
      if (checkValidation) return;
    }
    handleSubmit?.(isSubmitted, contents);
  };

  return (
    <>
      <S.SheetContainer>
        <Title text={title} />
        <form>
          <Textarea
            id="0"
            label={contents[0].questionContent}
            value={sheets[0].answerContent || ''}
            handleChangeContent={handleChangeContent(0)}
            isSubmit={isSubmit}
            isView={isView}
          />
          <Textarea
            id="1"
            label={contents[1].questionContent}
            value={contents[1].answerContent || ''}
            handleChangeContent={handleChangeContent(1)}
            isSubmit={isSubmit}
            isView={isView}
          />
          <Textarea
            id="2"
            label={contents[2].questionContent}
            value={contents[2].answerContent || ''}
            handleChangeContent={handleChangeContent(2)}
            isSubmit={isSubmit}
            isView={isView}
          />
          {!isView && (
            <S.ButtonContainer>
              <S.FirstButton onClick={onSubmitButton}>임시저장</S.FirstButton>
              <S.SecondButton onClick={onSubmitButton}>제출하기</S.SecondButton>
            </S.ButtonContainer>
          )}
        </form>
      </S.SheetContainer>
    </>
  );
};

export default Sheet;
