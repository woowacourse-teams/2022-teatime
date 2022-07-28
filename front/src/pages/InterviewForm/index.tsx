import Title from '@components/Title';
import Frame from '@components/Frame';
import Textarea from '@components/Textarea';

const InterviewForm = () => {
  return (
    <Frame>
      <div>
        <Title text="면담 내용 작성" />
        <form>
          <Textarea id="content1" label="이번 면담을 통해 논의하고 싶은 점은 무엇인가요?" />
        </form>
      </div>
    </Frame>
  );
};

export default InterviewForm;
