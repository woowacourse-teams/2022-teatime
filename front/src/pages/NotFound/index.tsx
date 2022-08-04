import NotFoundImage from '@assets/not-found.png';
import * as S from './styles';

const NotFound = () => {
  return (
    <S.Layout>
      <h1>404</h1>
      <p>페이지를 찾을 수 없습니다.</p>
      <img src={NotFoundImage} />
      <S.HomeLink to={'/'}>홈으로 이동하기</S.HomeLink>
    </S.Layout>
  );
};

export default NotFound;
