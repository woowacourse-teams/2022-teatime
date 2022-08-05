import Frame from '@components/Frame';
import SheetInfo from '@components/SheetInfo/index';

const ViewSheet = () => {
  return (
    <Frame>
      <SheetInfo
        title="작성한 내용"
        firstButton="뒤로 가기"
        secondButton="히스토리"
        isCoach
        isView
      />
    </Frame>
  );
};

export default ViewSheet;
