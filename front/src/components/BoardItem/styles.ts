import styled from 'styled-components';

const BoardItemContainer = styled.div<{ color: string }>`
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 160px;
  margin: 10px 0px;
  padding: 15px;
  background-color: ${({ theme }) => theme.colors.WHITE};
  border-left: 10px solid ${(props) => props.color};
  border-top-right-radius: 10px;
  border-bottom-right-radius: 10px;
  cursor: grab;

  &:active {
    cursor: grabbing;
  }
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
    color: ${({ theme }) => theme.colors.GRAY_800};
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
  border-radius: 15px;
  margin-right: 10px;
`;

const BottomSection = styled.div<{ color: string }>`
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
    background-color: ${(props) => props.color};
    font-weight: bold;
    cursor: pointer;

    &:hover {
      opacity: 0.6;
      transition: ease-in-out 0.2s;
    }
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
