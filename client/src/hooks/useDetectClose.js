import { useState, useEffect } from 'react';

const useDetectClose = (el, initialState) => {
  const [isOpen, setIsOpen] = useState(initialState);

  useEffect(() => {
    const onClick = (e) => {
      if (el.current !== null && !el.current.contains(e.target)) {
        setIsOpen(!isOpen);
      }
    };

    if (isOpen) {
      window.addEventListener('click', onClick);
    }

    return () => {
      window.removeEventListener('click', onClick);
    };
  }, [isOpen, el]);
  return [isOpen, setIsOpen];
};

export default useDetectClose;