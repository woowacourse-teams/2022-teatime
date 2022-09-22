import * as S from './styles';

import LoadingImage from '@assets/loading.gif';

interface LoadingProps {
  text?: string;
}

const Loading = ({ text }: LoadingProps) => {
  return (
    <S.ImageContainer>
      {!text && <img src={LoadingImage} alt="로딩 이미지" />}
      {text && <div>{text}</div>}
    </S.ImageContainer>
  );
};

export default Loading;
