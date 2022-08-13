import reactDom from 'react-dom';

interface PortalProps {
  id: string;
  children: React.ReactNode;
}

const Portal = ({ id, children }: PortalProps) => {
  const element = document.getElementById(id) as HTMLElement;
  return reactDom.createPortal(children, element);
};

export default Portal;
