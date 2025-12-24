// src/components/AddToCalendar.tsx
import React, { useState } from 'react';
import { IconButton, Menu, MenuItem, ListItemIcon, ListItemText } from '@mui/material';
import AddToCalendarIcon from '@mui/icons-material/CalendarMonth';
import GoogleIcon from '@mui/icons-material/Google';
import AppleIcon from '@mui/icons-material/Apple';
import DownloadIcon from '@mui/icons-material/Download'; // Для Outlook

// Импортируем все необходимое из новой, правильной библиотеки
import { google, outlook, office365, yahoo, ics } from "calendar-link";

// Описываем, какие данные ожидает наш компонент
interface EventData {
  title: string;
  description?: string;
  location?: string;
  start: Date;
  end: Date;
}

interface AddToCalendarProps {
  event: EventData;
}

export const AddToCalendar: React.FC<AddToCalendarProps> = ({ event }) => {
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);

  const handleClick = (e: React.MouseEvent<HTMLElement>) => setAnchorEl(e.currentTarget);
  const handleClose = () => setAnchorEl(null);

  // --- НОВАЯ ЛОГИКА: Создаем объект события для calendar-link ---
  // Библиотека сама рассчитает продолжительность
  const calendarEvent = {
    title: event.title,
    description: event.description,
    start: event.start,
    end: event.end,
    location: event.location,
  };

  return (
    <>
      <IconButton onClick={handleClick} size="small" aria-label="Добавить в календарь">
        <AddToCalendarIcon />
      </IconButton>
      <Menu anchorEl={anchorEl} open={open} onClose={handleClose}>
        
        {/* Google Calendar */}
        <MenuItem component="a" href={google(calendarEvent)} target="_blank" rel="noopener noreferrer" onClick={handleClose}>
          <ListItemIcon><GoogleIcon fontSize="small" /></ListItemIcon>
          <ListItemText>Google</ListItemText>
        </MenuItem>

        {/* Apple Calendar - использует data:text/calendar... */}
        <MenuItem component="a" href={ics(calendarEvent)} onClick={handleClose}>
          <ListItemIcon><AppleIcon fontSize="small" /></ListItemIcon>
          <ListItemText>Apple Calendar</ListItemText>
        </MenuItem>

        {/* Outlook Calendar - использует веб-ссылку */}
        <MenuItem component="a" href={outlook(calendarEvent)} target="_blank" rel="noopener noreferrer" onClick={handleClose}>
          <ListItemIcon><DownloadIcon fontSize="small" /></ListItemIcon>
          <ListItemText>Outlook</ListItemText>
        </MenuItem>
        
      </Menu>
    </>
  );
};