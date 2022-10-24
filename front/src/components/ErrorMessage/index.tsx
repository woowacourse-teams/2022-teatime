import { useContext } from 'react';

import { UserStateContext } from '@context/UserProvider';
import { ROUTES } from '@constants/index';
import NotFoundImage from '@assets/not-found.png';
import * as S from './styles';

interface ErrorMessageProps {
  title: string;
  description: string;
}

const ErrorMessage = ({ title, description }: ErrorMessageProps) => {
  const { userData } = useContext(UserStateContext);

  return (
    <S.Container>
      <h1>{title}</h1>
      <p>{description}</p>
      <img src={NotFoundImage} />
      <S.HomeLink to={userData ? `/${userData.role.toLowerCase()}` : ROUTES.HOME}>
        홈으로 이동하기
      </S.HomeLink>
    </S.Container>
  );
};

export default ErrorMessage;
