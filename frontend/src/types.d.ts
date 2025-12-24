import 'react';

declare module 'react' {
  interface HTMLAttributes<T> {
    // Расширяем все HTML-элементы
    [key: string]: any;
  }
}

// Расширяем JSX.IntrinsicElements напрямую
declare global {
  namespace JSX {
    interface IntrinsicElements {
      'add-to-calendar-button': React.DetailedHTMLProps<
        React.HTMLAttributes<HTMLElement>,
        HTMLElement
      > & {
        name?: string;
        startDate?: string;
        startTime?: string;
        endTime?: string;
        timeZone?: string;
        location?: string;
        options?: string;
        light?: boolean | 'true' | 'false';
        buttonStyle?: string;
        [key: string]: any;
      };
    }
  }
}

export {};
