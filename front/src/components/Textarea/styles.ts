import styled from 'styled-components';

const TextareaContainer = styled.div`
  margin: 28px 0;
`;

const Label = styled.label`
  font-weight: bold;
  color: ${({ theme }) => theme.colors.BLUE_800};
`;

const Textarea = styled.textarea<{ isFocus: boolean; isView: boolean }>`
  width: 100%;
  height: ${({ isView }) => (isView ? '180px' : '92px')};
  margin-top: 12px;
  padding: 10px;
  border: 1px solid
    ${({ theme, isFocus }) => (isFocus ? theme.colors.RED_600 : theme.colors.GRAY_500)};
  border-radius: 8px;
  font-size: 15px;
  line-height: 19px;
  font-family: none;
  resize: ${({ isView }) => (isView ? 'vertical' : 'none')};
  ::-webkit-resizer {
    border-top: 50px solid transparent;
    border-bottom: 50px solid ${({ theme }) => theme.colors.BLUE_700};
    border-left: 50px solid transparent;
    border-right: 50px solid ${({ theme }) => theme.colors.BLUE_700};
    border-radius: 4px;
  }
`;

const Span = styled.span`
  font-size: 14px;
  color: ${({ theme }) => theme.colors.RED_600};
`;

export { TextareaContainer, Label, Textarea, Span };
