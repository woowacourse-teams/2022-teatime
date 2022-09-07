import styled from 'styled-components';

const SheetContainer = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
`;

const CompleteButtonWrapper = styled.div`
  button {
    width: 100%;
    height: 50px;
    border-radius: 10px;
    border: none;
    background-color: #e5837c;
    font-size: 20px;
    color: white;
    cursor: pointer;
    margin-top: 10px;
    bottom: 20px;
  }
`;

export { CompleteButtonWrapper, SheetContainer };
