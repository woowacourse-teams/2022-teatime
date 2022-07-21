import LoadingImage from '@assets/loading.gif';
import styled from 'styled-components';

const Loading = () => {
  return (
    <ImageContainer>
      <img src={LoadingImage} alt="로딩 이미지" />
    </ImageContainer>
  );
};

const ImageContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: calc(100vh - 150px);
`;

export default Loading;
