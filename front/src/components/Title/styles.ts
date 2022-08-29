import styled from 'styled-components';

const TitleWrapper = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  padding: 15px;
  border: 2px solid ${({ theme }) => theme.colors.GREEN_900};
  border-radius: 10px;
`;

const Title = styled.h1`
  text-align: center;
  font-size: 24px;
  color: ${({ theme }) => theme.colors.GREEN_900};

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    font-size: 16px;
  }
`;

const HighLightText = styled(Title)<{ hightlightColor?: string }>`
  margin: 0 10px;
  border-bottom: 4px solid ${(props) => props.hightlightColor};
`;

export { TitleWrapper, Title, HighLightText };
