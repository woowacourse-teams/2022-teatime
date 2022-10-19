import * as S from './styles';

const SkeletonCard = () => {
  return (
    <S.SkeletonContainer>
      <S.SkeletonInner>
        <S.SkeletonImageWrapper>
          <div />
        </S.SkeletonImageWrapper>
        <span></span>
        <S.SkeletonButtonWrapper>
          <div />
        </S.SkeletonButtonWrapper>
      </S.SkeletonInner>
    </S.SkeletonContainer>
  );
};

export default SkeletonCard;
