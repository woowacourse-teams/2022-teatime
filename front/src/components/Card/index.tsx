import { useRef } from 'react';

import useIntersectionObserver from '@hooks/useIntersectionObserver';
import * as S from './styles';
import RequestIcon from '@assets/request.svg';
import PersonIcon from '@assets/person.svg';

interface CardProps {
  image: string;
  name: string;
  description: string;
  buttonName: string;
  isPossible?: boolean;
  onClick?: (e: React.MouseEvent) => void;
  isPreview?: boolean;
}

const Card = ({
  name,
  image,
  description,
  buttonName,
  isPossible,
  onClick,
  isPreview,
}: CardProps) => {
  const imgRef = useRef<HTMLImageElement | null>(null);
  const entry = useIntersectionObserver(imgRef, { rootMargin: '5%' });
  const isVisible = entry?.isIntersecting;

  return (
    <S.CardContainer onClick={onClick} isPossible={!!isPossible} isPreview={!!isPreview}>
      <S.IconWrapper>
        <img id="request" src={RequestIcon} alt="티타임 요청 보내기" />
      </S.IconWrapper>
      <S.CardWrapper>
        <S.ImageWrapper>
          <img src={isVisible ? image : PersonIcon} alt={`${name} 카드 이미지`} ref={imgRef} />
        </S.ImageWrapper>
        <span>{name}</span>
        <p>{description}</p>
        <S.ButtonWrapper>
          <button>{buttonName}</button>
        </S.ButtonWrapper>
      </S.CardWrapper>
    </S.CardContainer>
  );
};

export default Card;
