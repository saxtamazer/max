// src/layouts/MainLayout.tsx
import React, { useState, useEffect } from 'react';
import { Outlet, useNavigate, useLocation } from 'react-router-dom';
import { Box, Paper, BottomNavigation, BottomNavigationAction } from '@mui/material';
import ArticleIcon from '@mui/icons-material/Article';
import EventIcon from '@mui/icons-material/Event';
import PollIcon from '@mui/icons-material/Poll';

export const MainLayout: React.FC = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const [value, setValue] = useState(location.pathname);

    // Синхронизируем активную вкладку с текущим URL
    useEffect(() => {
        setValue(location.pathname);
    }, [location.pathname]);

    return (
        <Box sx={{ pb: 7 }}> {/* Добавляем отступ снизу, равный высоте панели */}
            
            {/* Здесь будут отображаться наши страницы */}
            <Outlet />

            <Paper 
                sx={{ 
                    position: 'fixed', 
                    bottom: 0, 
                    left: 0, 
                    right: 0,
                    // --- Стили для эффекта "Liquid Glass" ---
                    backgroundColor: 'rgba(255, 255, 255, 0.7)', // Полупрозрачный фон
                    backdropFilter: 'blur(10px)', // Размытие
                    WebkitBackdropFilter: 'blur(10px)', // Для Safari
                    borderTop: '1px solid rgba(0, 0, 0, 0.12)',
                }} 
                elevation={3}
            >
                <BottomNavigation
                    showLabels
                    value={value}
                    onChange={(event, newValue) => {
                        navigate(newValue);
                    }}
                    sx={{ backgroundColor: 'transparent' }} // делаем фон самой навигации прозрачным
                >
                    <BottomNavigationAction label="База знаний" value="/" icon={<ArticleIcon />} />
                    <BottomNavigationAction label="Расписание" value="/schedule" icon={<EventIcon />} />
                    <BottomNavigationAction label="Опросы" value="/polls" icon={<PollIcon />} />
                </BottomNavigation>
            </Paper>
        </Box>
    );
};
