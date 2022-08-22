import styled from 'styled-components';

const ContentList = styled.ul`
  position: absolute;
  z-index: 100;
  width: 150px;
  top: 48px;
  background-color: ${({ theme }) => theme.colors.WHITE};
  box-shadow: 0 0 10px rgb(190, 190, 190);
  border-radius: 8px;
  padding: 0;
  overflow: hidden;

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

export { ContentList };
