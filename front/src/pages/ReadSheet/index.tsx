import Frame from '@components/Frame';
import Sheet from '@components/Sheet/index';

const ReadSheet = () => {
  return (
    <Frame>
      <Sheet title="작성한 내용" firstButton="뒤로 가기" secondButton="히스토리" isRead />
    </Frame>
  );
};

export default ReadSheet;
