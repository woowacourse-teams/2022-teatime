import Frame from '@components/Frame';
import Sheet from '@components/Sheet/index';

const AddSheet = () => {
  return (
    <Frame>
      <Sheet title="면담 내용 작성" firstButton="임시 저장" secondButton="제출하기" />
    </Frame>
  );
};

export default AddSheet;
