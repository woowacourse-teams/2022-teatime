import styled, { css } from 'styled-components';

const ProfileContainer = styled.div`
  position: relative;
`;

const ProfileWrapper = styled.div`
  display: flex;
  align-items: center;
  padding: 4px 6px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.3);
  border-radius: 90px;
  transition: box-shadow 0.2s ease;
  cursor: pointer;

  :hover {
    box-shadow: 0 1px 8px rgba(0, 0, 0, 0.3);
  }

  span {
    font-weight: bold;
    font-size: 14px;
    margin: 0 10px;
  }

  img {
    width: 32px;
    height: 32px;
    border-radius: 50%;
  }
`;

const ContentList = styled.div<{ isActive: boolean }>`
  position: absolute;
  z-index: 100;
  width: 150px;
  top: 48px;
  background-color: ${({ theme }) => theme.colors.WHITE};
  box-shadow: 0 0 10px rgb(190, 190, 190);
  border-radius: 8px;
  opacity: 0;
  overflow: hidden;
  visibility: hidden;
  transform: translateY(-10px);
  transition: opacity 0.3s ease, transform 0.3s ease, visibility 0.3s;

  ${(props) =>
    props.isActive &&
    css`
      opacity: 1;
      visibility: visible;
      transform: translateY(0);
    `}

  ul {
    padding: 0;
  }

  li {
    padding: 8px;
    font-weight: bold;
    color: ${({ theme }) => theme.colors.GRAY_600};
    text-align: center;
    list-style: none;
    cursor: pointer;

    :hover {
      background-color: ${({ theme }) => theme.colors.GRAY_300};
    }
  }
`;

export { ProfileContainer, ProfileWrapper, ContentList };
