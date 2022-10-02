import styled from 'styled-components';

const Container = styled.ul`
  display: flex;
  justify-content: flex-start;
  padding-left: 0;
  margin-bottom: 25px;
  gap: 5px;
  list-style: none;

  @media screen and (${({ theme }) => theme.devices.tablet}) {
    justify-content: center;
  }
`;

const ListItem = styled.li<{ isSelected?: boolean }>`
  width: 100px;
  padding: 5px 10px;
  text-align: center;
  color: ${({ isSelected, theme }) => (isSelected ? theme.colors.BLACK : theme.colors.GRAY_300)};
  background-color: ${({ isSelected, theme }) =>
    isSelected ? theme.colors.YELLOW_200 : theme.colors.WHITE};
  border: 2px solid ${({ theme }) => theme.colors.YELLOW_200};
  border-radius: 10px;
  cursor: pointer;
  font-size: 15px;

  @media screen and (${({ theme }) => theme.devices.mobileXL}) {
    width: 50%;
  }
`;

export { Container, ListItem };
