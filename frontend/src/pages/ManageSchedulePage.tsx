// src/pages/ManageSchedulePage.tsx
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Container, Typography, Button, Paper, CircularProgress, Box, Alert, IconButton, Dialog, DialogTitle, DialogContent, DialogActions, TextField } from '@mui/material';
import AddCircleIcon from '@mui/icons-material/AddCircle';
import EditIcon from '@mui/icons-material/Edit';
import DeleteIcon from '@mui/icons-material/Delete';

// Определяем типы для событий, чтобы использовать их в состоянии
interface ScheduleEventData {
    id: number;
    subject: string;
    teacher: string;
    room: string;
    start_time: string;
    end_time: string;
    week_type: 'EVEN' | 'ODD' | 'BOTH';
}

export const ManageSchedulePage: React.FC = () => {
    const [events, setEvents] = useState<ScheduleEventData[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [isHeadman, setIsHeadman] = useState(false); // Состояние для проверки прав

    // Состояние для модального окна добавления/редактирования
    const [openModal, setOpenModal] = useState(false);
    const [currentEvent, setCurrentEvent] = useState<Partial<ScheduleEventData> | null>(null);

    const fetchSchedule = () => {
        setLoading(true);
        // Запрашиваем данные с приватного URL для старост
        axios.get('/api/v1/schedule/manage/')
            .then(response => {
                setEvents(response.data);
                setIsHeadman(true);
            })
            .catch(err => {
                if (err.response && err.response.status === 403) {
                    setError("Доступ запрещен. Эта страница только для старост.");
                    setIsHeadman(false);
                } else {
                    setError("Не удалось загрузить расписание для управления.");
                }
                console.error("Error fetching headman schedule!", err);
            })
            .finally(() => setLoading(false));
    };

    useEffect(fetchSchedule, []);

    const handleFileUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files?.[0];
        if (file) {
            const formData = new FormData();
            formData.append('schedule_file', file);
            
            // Отправляем formData POST-запросом
            axios.post('/api/v1/schedule/manage/', formData, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                }
            })
            .then(() => {
                alert('Файл успешно загружен! Расписание обновлено.');
                fetchSchedule(); // Обновляем список после загрузки
            })
            .catch(err => {
                alert('Ошибка при загрузке файла.');
                console.error("File upload error:", err);
            });
        }
    };
    
    // Функции-заглушки для модального окна
    const handleOpenCreateModal = () => {
        setCurrentEvent({}); // Пустой объект для нового события
        setOpenModal(true);
    };

    const handleOpenEditModal = (event: ScheduleEventData) => {
        setCurrentEvent(event);
        setOpenModal(true);
    };

    const handleCloseModal = () => {
        setOpenModal(false);
        setCurrentEvent(null);
    };

    const handleSaveChanges = () => {
        // TODO: Логика отправки данных (создание или обновление) на бэкенд
        console.log("Saving event:", currentEvent);
        alert("Функционал сохранения находится в разработке.");
        handleCloseModal();
        // После успешного сохранения нужно будет вызвать fetchSchedule()
    };
    
    const handleDeleteEvent = (eventId: number) => {
        // TODO: Логика отправки DELETE-запроса на бэкенд
        if (window.confirm("Вы уверены, что хотите удалить это занятие?")) {
            console.log("Deleting event with id:", eventId);
            alert("Функционал удаления находится в разработке.");
            // После успешного удаления нужно будет вызвать fetchSchedule()
        }
    };


    if (loading) {
        return <Box display="flex" justifyContent="center" mt={4}><CircularProgress /></Box>;
    }
    
    if (error && !isHeadman) {
        return <Container maxWidth="md" sx={{ py: 4 }}><Alert severity="error">{error}</Alert></Container>;
    }

    return (
        <Container maxWidth="md" sx={{ py: 4 }}>
            <Typography variant="h4" component="h1" gutterBottom color="primary">
                Управление расписанием
            </Typography>

            {/* Блок для загрузки файла */}
            <Paper elevation={2} sx={{ p: 3, mb: 4 }}>
                <Typography variant="h6">Загрузить расписание из файла</Typography>
                <Typography variant="body2" color="text.secondary" paragraph>
                    Подготовьте файл в формате CSV с колонками: subject, teacher, room, start_time, end_time, week_type.
                </Typography>
                <Button variant="contained" component="label">
                    Выбрать CSV файл
                    <input type="file" hidden accept=".csv" onChange={handleFileUpload} />
                </Button>
            </Paper>

            {/* Блок для ручного управления */}
            <Paper elevation={2} sx={{ p: 3 }}>
                <Box display="flex" justifyContent="space-between" alignItems="center" mb={2}>
                    <Typography variant="h6">Текущее расписание группы</Typography>
                    <Button variant="contained" startIcon={<AddCircleIcon />} onClick={handleOpenCreateModal}>
                        Добавить занятие
                    </Button>
                </Box>
                {events.length > 0 ? events.map(event => (
                    <Paper key={event.id} variant="outlined" sx={{ p: 2, mb: 1, display: 'flex', alignItems: 'center' }}>
                        <Box flexGrow={1}>
                            <Typography fontWeight="bold">{event.subject} <Typography variant="caption" color="text.secondary">({event.week_type})</Typography></Typography>
                            <Typography variant="body2">{new Date(event.start_time).toLocaleString('ru', { weekday: 'long', hour: '2-digit', minute: '2-digit' })}</Typography>
                            <Typography variant="body2" color="text.secondary">{event.teacher}, ауд. {event.room}</Typography>
                        </Box>
                        <IconButton color="primary" onClick={() => handleOpenEditModal(event)}>
                            <EditIcon />
                        </IconButton>
                        <IconButton color="error" onClick={() => handleDeleteEvent(event.id)}>
                            <DeleteIcon />
                        </IconButton>
                    </Paper>
                )) : (
                    <Typography>Расписание для вашей группы еще не добавлено.</Typography>
                )}
            </Paper>
            
            {/* Модальное окно для создания/редактирования */}
            <Dialog open={openModal} onClose={handleCloseModal} fullWidth maxWidth="sm">
                <DialogTitle>{currentEvent?.id ? 'Редактировать занятие' : 'Новое занятие'}</DialogTitle>
                <DialogContent>
                    <TextField margin="dense" label="Название предмета" fullWidth variant="standard" defaultValue={currentEvent?.subject || ''} />
                    <TextField margin="dense" label="Преподаватель" fullWidth variant="standard" defaultValue={currentEvent?.teacher || ''} />
                    <TextField margin="dense" label="Аудитория" fullWidth variant="standard" defaultValue={currentEvent?.room || ''} />
                    {/* 
                        TODO: Для полей с датой и временем, а также с выбором типа недели
                        лучше использовать специализированные компоненты, например,
                        DateTimePicker от @mui/x-date-pickers и Select от @mui/material.
                        Пока что это просто текстовые поля-заглушки.
                    */}
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseModal}>Отмена</Button>
                    <Button onClick={handleSaveChanges} variant="contained">Сохранить</Button>
                </DialogActions>
            </Dialog>
        </Container>
    );
};