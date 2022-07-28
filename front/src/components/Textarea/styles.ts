import styled from 'styled-components';

const Label = styled.label`
  color: ${({ theme }) => theme.colors.GRAY_500};
`;

const Textarea = styled.textarea`
  width: 100%;
  height: 180px;
  padding: 8px;
  border-radius: 4px;
  resize: none;
`;

export { Label, Textarea };
