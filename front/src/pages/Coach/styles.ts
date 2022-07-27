import styled from 'styled-components';

const Layout = styled.div`
  display: flex;
  justify-content: center;
  flex-direction: column;
  padding: 120px 200px;
`;

const Menu = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
  margin-bottom: 20px;
  border-bottom: 1px solid #3b3b3b;

  span {
    font-size: 24px;
    font-weight: bold;
    margin-right: 20px;
    cursor: pointer;
  }

  span:last-child {
    color: #aaaaaa;
  }

  button {
    position: relative;
    bottom: 15px;
    font-size: 18px;
    font-weight: bold;
    border: none;
    border-radius: 8px;
    padding: 10px 20px;
    background-color: #797d6b;
    color: white;
    cursor: pointer;
    :hover {
      transform: scale(1.1);
    }
  }
`;

export { Layout, Menu };
