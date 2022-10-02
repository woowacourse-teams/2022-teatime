import styled, { css } from 'styled-components';

const Container = styled.ul`
  display: flex;
  list-style: none;
  padding-left: 0;
`;

const ListItem = styled.li<{ isSelected?: boolean }>`
  padding: 5px;
  color: ${({ isSelected, theme }) => (isSelected ? theme.colors.BLACK : theme.colors.GRAY_300)};
  cursor: pointer;
`;

export { Container, ListItem };
