import Title from '@components/Title';
import Frame from '@components/Frame';
import Textarea from '@components/Textarea';
import * as S from './styles';

const InterviewForm = () => {
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
          <Textarea id="content1" label="이번 면담을 통해 논의하고 싶은 내용" />
          <Textarea id="content1" label="최근에 자신이 긍정적으로 보는 시도와 변화" />
          <Textarea id="content1" label="이번 면담을 통해 생기기를 원하는 변화" />
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
