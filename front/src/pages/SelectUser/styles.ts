import styled from 'styled-components';

const SelectUserContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  height: 500px;

  button {
    width: 300px;
    height: 200px;
    margin-left: 50px;
    border: none;
    border-radius: 8px;
    font-size: 50px;
    font-weight: bold;
    cursor: pointer;

    :hover {
      border: 1px solid #000;
    }
  }
`;

export { SelectUserContainer };
