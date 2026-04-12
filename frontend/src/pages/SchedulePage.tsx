// src/pages/SchedulePage.tsx
import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import { Container, Typography, CircularProgress, Box, Paper, Alert, Chip, Stack, TextField, Button, Autocomplete } from '@mui/material';
import SearchIcon from '@mui/icons-material/Search'

import FullCalendar from '@fullcalendar/react';
import listPlugin from '@fullcalendar/list';
import { EventContentArg } from '@fullcalendar/core';

import { AddToCalendar } from '../components/AddToCalendar';

// --- Интерфейсы ---
interface CalendarEvent {
    title: string;
    start: string;
    end: string;
    extendedProps: {
        teacher: string;
        room: string;
        weekType: 'EVEN' | 'ODD' | 'BOTH';
    }
}

interface ScheduleResponse {
    events: any[];
    current_week_is_even: boolean;
}

const WeekTypeChip: React.FC<{ weekType: 'EVEN' | 'ODD' | 'BOTH' }> = ({ weekType }) => {
    if (weekType === 'BOTH') return <Chip label="Еженедельно" size="small" variant="outlined" color="success" />;
    if (weekType === 'EVEN') return <Chip label="Четная" size="small" variant="outlined" color="info" />;
    if (weekType === 'ODD') return <Chip label="Нечетная" size="small" variant="outlined" color="secondary" />;
    return null;
};


export const SchedulePage: React.FC = () => {
    const[groupName, setGroupName] = useState('ДА 01-24')
    const [events, setEvents] = useState<CalendarEvent[]>([]);
    const [isCurrentWeekEven, setIsCurrentWeekEven] = useState<boolean | null>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const[allGroups, setAllGroups] = useState<string[]>([]);

    const fetchSchedule = useCallback((targetGroup: string) => {
        if (!targetGroup.trim()) {
            setError("Введите название группы")
            return;
        }

        setLoading(true);
        setError('');

        axios.get<ScheduleResponse>(`http://localhost:8081/api/v1/schedule/view/${targetGroup}`)
            .then(response => {
                const { events: rawEvents, current_week_is_even } = response.data;
                
                const formattedEvents = rawEvents.map((event: any) => ({
                    title: event.subject,
                    start: event.start_time,
                    end: event.end_time,
                    extendedProps: {
                        teacher: event.teacher,
                        room: event.room,
                        weekType: event.week_type,
                    }
                }));
                setEvents(formattedEvents);
                setIsCurrentWeekEven(current_week_is_even);
            })
            .catch(err => {
                console.error("Error fetching public schedule!", err);
                setError("Не удалось загрузить расписание. Проверьте название группы или попробуйте позже.");
            })
            .finally(() => setLoading(false));
        }, [])
    
    useEffect(() => {
        axios.get<string[]>('http://localhost:8081/api/v1/schedule/group')
        .then(res => setAllGroups(res.data))
        .catch(() => {});
    }, []); // Убрали зависимость, теперь загрузка идет один раз

    const handleKeyDown = (event: React.KeyboardEvent) => {
        if (event.key === 'Enter') {
            fetchSchedule(groupName);
        }
    }

    return (
        <Container maxWidth="md" sx={{ py: { xs: 2, sm: 4 } }}>
            <Typography 
                variant="h4" 
                component="h1" 
                gutterBottom 
                color="primary"
                sx={{ fontSize: { xs: '1.75rem', sm: '2.125rem' } }}
            >
                Расписание занятий
            </Typography>

            <Stack direction="row" spacing={2} sx={{ mb: 3 }} alignItems="stretch">
                <Autocomplete
                    freeSolo                          // можно вводить вручную
                    options={allGroups}
                    value={groupName}
                    onInputChange={(_, newValue) => setGroupName(newValue)}
                    onChange={(_, newValue) => {
                        if (newValue) {
                            setGroupName(newValue);
                            fetchSchedule(newValue);  // выбор из списка — сразу ищем
                        }
                    }}
                    filterOptions={(options, { inputValue }) =>
                        options
                            .filter(o => o.toLowerCase().includes(inputValue.toLowerCase()))
                            .slice(0, 5)              // показываем максимум 5
                    }
                    fullWidth
                    renderInput={(params) => (
                        <TextField
                            {...params}
                            label="Название группы"
                            variant="outlined"
                            size="small"
                            placeholder="Например: ДА 01-24"
                            onKeyDown={handleKeyDown}
                        />
                    )}
                />
                <Button
                    variant="contained"
                    onClick={() => fetchSchedule(groupName)}
                    startIcon={<SearchIcon />}
                    disabled={loading}
                    sx={{ minWidth: '120px' }}
                >
                    Найти
                </Button>
            </Stack>
            
            {isCurrentWeekEven !== null && !loading && !error && (
                <Typography variant="body2" color="text.secondary" paragraph>
                    Текущая неделя: {isCurrentWeekEven === null ? '...' : (isCurrentWeekEven ? <b>Четная</b> : <b>Нечетная</b>)}. 
                    События другой недели отображаются бледнее.
                </Typography>
            )}

            {loading && 
                <Box display = "flex" justifyContent="center" mt={4}>
                    <CircularProgress/>
                </Box>
            }

            {error && <Alert severity="error" sx={{mb:2}}>{error}</Alert>}


            {!loading && <Paper elevation={2} sx={{ p: { xs: 1, sm: 2 } }}>
                <FullCalendar
                    plugins={[listPlugin]}
                    initialView="listWeek"
                    // --- ГЛАВНОЕ ИСПРАВЛЕНИЕ ---
                    firstDay={1} // 1 = Понедельник
                    // --------------------------
                    headerToolbar={false}
                    locale="ru"
                    events={events}
                    noEventsText="На этой неделе занятий нет"
                    eventContent={(eventInfo: EventContentArg) => {
                        if (!eventInfo.event.start || !eventInfo.event.end || isCurrentWeekEven === null) {
                            return null;
                        }

                        const eventWeekType = eventInfo.event.extendedProps.weekType as 'EVEN' | 'ODD' | 'BOTH';
                        const isActive = eventWeekType === 'BOTH' || 
                                         (isCurrentWeekEven && eventWeekType === 'EVEN') || 
                                         (!isCurrentWeekEven && eventWeekType === 'ODD');

                        const teachers = (eventInfo.event.extendedProps.teacher as string[]) || [];
                        const rooms = (eventInfo.event.extendedProps.room as string[]) || [];
                        const maxLength = Math.max(teachers.length, rooms.length)
                        

                        const calendarEvent = {
                            title: eventInfo.event.title,
                            description: `${teachers} ${rooms}`,
                            start: new Date(eventInfo.event.start),
                            end: new Date(eventInfo.event.end),
                        };

                        return (
                            <Box sx={{ 
                                display: 'flex', 
                                width: '100%', 
                                alignItems: 'center', 
                                py: { xs: 0.5, sm: 1 },
                                opacity: isActive ? 1 : 0.4,
                                transition: 'opacity 0.3s ease-in-out',
                            }}>
                                <Box sx={{ flexGrow: 1 }}>
                                    <Box sx={{display: 'flex', alignItems: 'center', gap: 1, mb: 0.5}}>
                                        <Typography variant="body1" fontWeight="bold" sx={{ fontSize: { xs: '0.9rem', sm: '1rem' } }}>
                                            {eventInfo.event.title}
                                        </Typography>
                                        <WeekTypeChip weekType={eventWeekType} />
                                    </Box>
                                    <Box sx = {{
                                        display: 'flex',
                                        flexDirection: 'column',
                                        gap: 0.5
                                    }}>
                                        {
                                            Array.from({length: maxLength}).map((_, index) => {
                                                const teacher = teachers[index] || '';
                                                const room = rooms[index] || '';

                                                if (!teacher && !room) return null;

                                                return (
                                                    <Box key = {index} sx = {{
                                                        display: 'grid',
                                                        gridTemplateColumns: '1fr auto',
                                                        width: '300px',
                                                        gap: 2,
                                                        alignItems: 'center'
                                                    }}>
                                                        <Typography variant="body2" color="text.secondary" 
                                                        sx={{
                                                            fontSize: { xs: '0.75rem', sm: '0.875rem' } 
                                                        }}>
                                                            {teacher}
                                                        </Typography>
                                                       {room && (<Typography variant="body2" color="text.secondary" 
                                                       sx={{ 
                                                            fontSize: { xs: '0.75rem', sm: '0.875rem' }, 
                                                            whiteSpace: 'nowrap' 
                                                        }}> {room}
                                                        </Typography>)}
                                                    </Box>
                                                );
                                            })}                                        
                                    </Box>
                                </Box>
                                <AddToCalendar event={calendarEvent} />
                            </Box>
                        );
                    }}
                />
            </Paper>}
        </Container>
    );
};