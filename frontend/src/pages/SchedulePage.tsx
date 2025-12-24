// src/pages/SchedulePage.tsx
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Container, Typography, CircularProgress, Box, Paper, Alert, Chip } from '@mui/material';

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
    const [events, setEvents] = useState<CalendarEvent[]>([]);
    const [isCurrentWeekEven, setIsCurrentWeekEven] = useState<boolean | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    
    useEffect(() => {
        axios.get<ScheduleResponse>('/api/v1/schedule/view/')
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
                setError("Не удалось загрузить расписание. Попробуйте позже.");
            })
            .finally(() => setLoading(false));
    }, []); // Убрали зависимость, теперь загрузка идет один раз

    if (loading) {
        return <Box display="flex" justifyContent="center" mt={4}><CircularProgress /></Box>;
    }

    if (error) {
        return <Container maxWidth="md" sx={{ py: 4 }}><Alert severity="error">{error}</Alert></Container>;
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
            <Typography variant="body2" color="text.secondary" paragraph>
                Текущая неделя: {isCurrentWeekEven === null ? '...' : (isCurrentWeekEven ? <b>Четная</b> : <b>Нечетная</b>)}. 
                События другой недели отображаются бледнее.
            </Typography>
            <Paper elevation={2} sx={{ p: { xs: 1, sm: 2 } }}>
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

                        const calendarEvent = {
                            title: eventInfo.event.title,
                            description: `Преподаватель: ${eventInfo.event.extendedProps.teacher}`,
                            location: `Аудитория: ${eventInfo.event.extendedProps.room}`,
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
                                    <Typography variant="body2" color="text.secondary" sx={{ fontSize: { xs: '0.75rem', sm: '0.875rem' } }}>
                                        {eventInfo.event.extendedProps.teacher as string}
                                    </Typography>
                                    <Typography variant="body2" color="text.secondary" sx={{ fontSize: { xs: '0.75rem', sm: '0.875rem' } }}>
                                        Аудитория: {eventInfo.event.extendedProps.room as string}
                                    </Typography>
                                </Box>
                                <AddToCalendar event={calendarEvent} />
                            </Box>
                        );
                    }}
                />
            </Paper>
        </Container>
    );
};