import { useState, useEffect } from 'react';

const useDarkMode = () => {
  const [theme, setTheme] = useState('light');
  const [mountedComponent, setMountedComponent] = useState(false)

  const setMode = (mode) => {
    window.localStorage.setItem('theme', mode);
    setTheme(mode);
  };

  const toggleTheme = () => {
    theme === 'light' ? setMode('dark') : setMode('light');
  };

  useEffect(() => {
    const localTheme = window.localStorage.getItem('theme');
    localTheme ? setTheme(localTheme) : setMode('light');
    setMountedComponent(true);
  }, []);

  return [theme, toggleTheme, mountedComponent];
};

export default useDarkMode;