import styled from 'styled-components';

const Layout = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 50px auto;
`;

const CardListContainer = styled.div`
  display: grid;
  grid-template-columns: repeat(4, 250px);
  gap: 40px;

  @media all and (max-width: 768px) {
    grid-template-columns: repeat(2, 250px);
  }

  @media all and (max-width: 480px) {
    grid-template-columns: repeat(2, 150px);
  }
`;

export { CardListContainer, Layout };
