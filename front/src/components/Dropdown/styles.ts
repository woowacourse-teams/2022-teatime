import styled, { css } from 'styled-components';

const ContentList = styled.ul<{ isActive: boolean }>`
  position: absolute;
  z-index: 100;
  width: 150px;
  right: 0;
  top: 48px;
  padding: 0;
  background-color: ${({ theme }) => theme.colors.WHITE};
  box-shadow: 0 0 10px rgb(190, 190, 190);
  border-radius: 8px;
  opacity: 0;
  overflow: hidden;
  visibility: hidden;
  transform: translateY(-10px);
  transition: opacity 0.2s ease, transform 0.2s ease, visibility 0.2s;

  ${(props) =>
    props.isActive &&
    css`
      opacity: 1;
      visibility: visible;
      transform: translateY(0);
    `}

  li {
    padding: 12px 0;
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
