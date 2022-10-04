import styled from 'styled-components';

const Select = styled.select`
  width: 100px;
  height: 35px;
  margin: 20px 0 10px;
  font-size: 16px;
  border: 2px solid ${({ theme }) => theme.colors.GRAY_500};
  border-radius: 5px;
  background: transparent;
  color: ${({ theme }) => theme.colors.GRAY_600};
  padding-left: 10px;
  outline: 0;
`;

export { Select };
