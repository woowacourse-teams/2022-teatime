import * as S from './styles';

import LoadingImage from '@assets/loading.gif';

interface LoadingProps {
  showImage: boolean;
  text?: string;
}

const Loading = ({ showImage, text }: LoadingProps) => {
  return (
    <S.ImageContainer>
      {showImage && <img src={LoadingImage} alt="로딩 이미지" />}
      {text && <div>{text}</div>}
    </S.ImageContainer>
  );
};

export default Loading;
