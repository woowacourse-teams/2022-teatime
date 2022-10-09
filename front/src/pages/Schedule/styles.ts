import styled from 'styled-components';
import { FadeIn } from '@styles/common';

const SelectStatusButton = styled.button`
  width: 100%;
  background-color: ${({ theme }) => theme.colors.BLUE_300};
  margin: 20px 0;
  padding: 10px;
  border: none;
  border-radius: 10px;
  font-size: 15px;
  cursor: pointer;
  animation: ${FadeIn} 0.6s;

  &:hover {
    opacity: 0.7;
  }
`;

export { SelectStatusButton };
