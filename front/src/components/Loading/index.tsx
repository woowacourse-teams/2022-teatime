import * as S from './styles';

import LoadingImage from '@assets/loading.gif';

const Loading = () => {
  return (
    <S.ImageContainer>
      <img src={LoadingImage} alt="로딩 이미지" />
    </S.ImageContainer>
  );
};

export default Loading;
