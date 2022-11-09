import { RefObject, useEffect, useState } from 'react';

const useIntersectionObserver = (
  elementRef: RefObject<Element>,
  { threshold = 0, root = null, rootMargin = '0%' }: IntersectionObserverInit
) => {
  const [entry, setEntry] = useState<IntersectionObserverEntry>();

  const isIntersecting = entry?.isIntersecting;

  const updateEntry = ([entry]: IntersectionObserverEntry[]) => {
    setEntry(entry);
  };

  useEffect(() => {
    const node = elementRef?.current;

    if (isIntersecting || !node) return;

    const options = { threshold, root, rootMargin };
    const observer = new IntersectionObserver(updateEntry, options);

    observer.observe(node);

    return () => observer.disconnect();
  }, [elementRef?.current, threshold, root, rootMargin, isIntersecting]);

  return entry;
};

export default useIntersectionObserver;
