import reactDom from 'react-dom';
import type { PropsWithRequiredChildren } from '@typings/utils';

interface PortalProps {
  id: string;
}

const Portal = ({ id, children }: PropsWithRequiredChildren<PortalProps>) => {
  const element = document.getElementById(id) as HTMLElement;
  return reactDom.createPortal(children, element);
};

export default Portal;
