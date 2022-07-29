import { useState } from 'react';
import * as S from './styles';

import Title from '@components/Title';
import Frame from '@components/Frame';
import Textarea from '@components/Textarea';

const InterviewForm = () => {
  const [content, setContent] = useState([
    {
      questionNumber: 1,
      answerContent: '',
    },
    {
      questionNumber: 2,
      answerContent: '',
    },
    {
      questionNumber: 3,
      answerContent: '',
    },
  ]);

  const handleChangeContent = (index: number) => (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent((prevContent) => {
      const newContent = [...prevContent];
      newContent[index].answerContent = e.target.value;
      return newContent;
    });
  };

  return (
    <Frame>
      <S.InfoContainer>
        <p>포코</p>
        <p>🗓 07월 28일</p>
        <p>🕖 11 : 00</p>
      </S.InfoContainer>
      <S.InterviewContainer>
        <Title text="면담 내용 작성" />
        <form>
          <Textarea
            id="0"
            label="이번 면담을 통해 논의하고 싶은 내용"
            value={content[0].answerContent}
            handleChangeContent={handleChangeContent(0)}
          />
          <Textarea
            id="1"
            label="최근에 자신이 긍정적으로 보는 시도와 변화"
            value={content[1].answerContent}
            handleChangeContent={handleChangeContent(1)}
          />
          <Textarea
            id="2"
            label="이번 면담을 통해 생기기를 원하는 변화"
            value={content[2].answerContent}
            handleChangeContent={handleChangeContent(2)}
          />
          <S.ButtonContainer>
            <S.SaveButton>임시 저장</S.SaveButton>
            <S.SubmitButton>제출하기</S.SubmitButton>
          </S.ButtonContainer>
        </form>
      </S.InterviewContainer>
    </Frame>
  );
};

export default InterviewForm;
