import LoadingImage from '@assets/loading.gif';
import { ImageContainer } from './styles';

const Loading = () => {
  return (
    <ImageContainer>
      <img src={LoadingImage} alt="로딩 이미지" />
    </ImageContainer>
  );
};

export default Loading;
