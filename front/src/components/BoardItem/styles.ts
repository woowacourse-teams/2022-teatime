import styled from 'styled-components';

const BoardItemContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 160px;
  margin: 10px 0px;
  padding: 15px;
  border-left: 10px solid ${({ theme }) => theme.colors.ORANGE_600};
  background-color: ${({ theme }) => theme.colors.WHITE};
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
`;

const TopSection = styled.div`
  display: flex;
  justify-content: space-between;
`;

const DateContainer = styled.div`
  display: flex;
  flex-direction: column;

  div {
    margin-bottom: 5px;
  }

  img {
    width: 18px;
    height: 18px;
    vertical-align: top;
    margin-right: 5px;
  }

  span {
    color: #6d798e;
    font-weight: 600;
    font-size: 18px;
    letter-spacing: 1px;
  }
`;

const CloseIconWrapper = styled.div`
  display: flex;
  align-items: flex-start;

  img {
    width: 15px;
    height: 15px;
    cursor: pointer;
  }
`;

const ProfileImage = styled.img`
  width: 25px;
  height: 25px;
  margin-right: 10px;
`;

const BottomSection = styled.div`
  flex: 1;
  display: flex;
  justify-content: space-between;
  align-items: flex-end;

  div {
    display: flex;
    align-items: flex-end;
  }

  button {
    width: 100px;
    height: 25px;
    border: none;
    border-radius: 15px;
    color: #fff;
    background-color: ${({ theme }) => theme.colors.ORANGE_600};
    font-weight: bold;
    cursor: pointer;
  }
`;

export {
  BoardItemContainer,
  TopSection,
  DateContainer,
  CloseIconWrapper,
  ProfileImage,
  BottomSection,
};
