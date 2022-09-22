import styled from 'styled-components';

const ImageContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: calc(100vh - 50px);
  background-color: ${({ theme }) => theme.colors.WHITE};
  border-top: 2px solid ${({ theme }) => theme.colors.GRAY_200};
`;

export { ImageContainer };
