import styled from 'styled-components';

const TitleWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  padding: 15px;
  background-color: ${({ theme }) => theme.colors.GRAY_100};
  border-radius: 10px;
`;

const Title = styled.span`
  text-align: center;
  font-size: 24px;
  color: ${({ theme }) => theme.colors.GREEN_900};

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    font-size: 16px;
  }
`;

const HighLightText = styled(Title)<{ highlightColor?: string }>`
  margin: 0 10px;
  background: ${({ highlightColor }) =>
    `linear-gradient(to top, ${highlightColor} 50%, transparent 50%)`};
`;

export { TitleWrapper, Title, HighLightText };
