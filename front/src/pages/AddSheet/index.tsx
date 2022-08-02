import Frame from '@components/Frame';
import SheetInfo from '@components/SheetInfo/index';

const AddSheet = () => {
  return (
    <Frame>
      <SheetInfo title="면담 내용 작성" firstButton="임시 저장" secondButton="제출하기" />
    </Frame>
  );
};

export default AddSheet;
