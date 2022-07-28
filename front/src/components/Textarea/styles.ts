import styled from 'styled-components';

const TextareaContainer = styled.div`
  margin: 20px 0;
`;

const Label = styled.label`
  font-weight: bold;
  color: ${({ theme }) => theme.colors.BLUE_800};
`;

const Textarea = styled.textarea`
  width: 100%;
  height: 150px;
  margin: 12px 0;
  padding: 10px;
  border-radius: 4px;
  font-size: 15px;
  resize: none;
  word-spacing: -3px;
`;

export { TextareaContainer, Label, Textarea };
