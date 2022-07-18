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
    /* border-bottom: 2px solid #C3C9AD; */
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
    /* margin-bottom: 20px; */
    background-color: #c3c9ad;
    color: #3b3b3b;
    cursor: pointer;
    :hover {
      transform: scale(1.1);
    }
  }
`;

const Title = styled.h1`
  margin: 20px 0 10px 0;
  font-size: 20px;
  font-weight: bold;
`;

const Wrapper = styled.div`
  display: flex;
  flex-wrap: wrap;
  > div {
    display: flex;
    justify-content: space-between;
    width: 48%;
    margin: 0 12px 12px 0;
    padding: 14px;
    background: linear-gradient(25deg, #f8d57e, #f7ba20);
    border-radius: 8px;
  }

  span {
    font-size: 18px;
    font-weight: bold;
  }

  button {
    border: 1px solid #454545;
    padding: 4px 8px;
    margin-left: 8px;
    border-radius: 12px;
    background-color: transparent;
    cursor: pointer;

    :hover {
      transform: scale(1.1);
    }
  }
`;

export { Layout, Menu, Title, Wrapper };
