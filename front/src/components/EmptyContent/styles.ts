import styled from 'styled-components';

const Container = styled.div<{ width?: string; height?: string }>`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  margin: 30px 0;

  img {
    width: 80px;
    height: 100px;
  }

  span {
    margin: 15px 0;
    color: ${({ theme }) => theme.colors.GRAY_300};
  }
`;

export { Container };
