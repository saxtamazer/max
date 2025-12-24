// src/theme.ts
import { createTheme } from '@mui/material/styles';

export const theme = createTheme({
  palette: {
    mode: 'light', // Можете поменять на 'dark' для темной темы
    primary: {
      main: '#006400', // Классический темно-зеленый цвет
    },
    secondary: {
      main: '#f57c00', // Акцентный оранжевый, хорошо сочетается с зеленым
    },
    background: {
      default: '#f4f6f8', // Слегка серый фон, приятнее чисто-белого
      paper: '#ffffff',
    },
  },
  typography: {
    fontFamily: 'Roboto, sans-serif',
    h4: {
      fontWeight: 700,
    },
    h5: {
      fontWeight: 600,
    },
  },
  components: {
    // Добавим скругления для кнопок и карточек
    MuiButton: {
      styleOverrides: {
        root: {
          borderRadius: 8,
        },
      },
    },
    MuiPaper: {
      styleOverrides: {
        root: {
          borderRadius: 12,
        },
      },
    },
    MuiListItemButton: {
        styleOverrides: {
          root: {
            borderRadius: 8,
          },
        },
      },
  },
});