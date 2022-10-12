import * as S from './styles';

interface CardProps {
  image: string;
  name: string;
  description: string;
  buttonName: string;
  isPossible?: boolean;
  onClick?: () => void;
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
  return (
    <S.CardContainer onClick={onClick} isPossible={!!isPossible} isPreview={!!isPreview}>
      <div>
        <S.ImageWrapper>
          <img src={image} alt={`${name} 카드 이미지`} />
        </S.ImageWrapper>
        <span>{name}</span>
        <p>{description}</p>
        <S.ButtonWrapper>
          <button>{buttonName}</button>
        </S.ButtonWrapper>
      </div>
    </S.CardContainer>
  );
};

export default Card;
