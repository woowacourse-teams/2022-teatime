import styled from 'styled-components';

const Container = styled.ul`
  display: flex;
  justify-content: space-between;
  width: 90%;
  margin: 0 20px;
  list-style: none;
  padding-left: 0;
  height: 50px;
`;

const ListItem = styled.li<{ isSelected?: boolean }>`
  padding: 5px;
  color: ${({ isSelected, theme }) => (isSelected ? theme.colors.BLACK : theme.colors.GRAY_300)};
  cursor: pointer;
`;

export { Container, ListItem };
