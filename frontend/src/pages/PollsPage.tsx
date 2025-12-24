// src/pages/PollsPage.tsx
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Container, Typography, CircularProgress, Box, Paper, Accordion, AccordionSummary, AccordionDetails, RadioGroup, FormControlLabel, Radio, Button } from '@mui/material';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';

// ... (здесь будут интерфейсы для Poll, Question, Choice) ...

export const PollsPage: React.FC = () => {
    const [polls, setPolls] = useState<any[]>([]); // Замените any на интерфейсы
    const [loading, setLoading] = useState(true);

    const fetchPolls = () => {
        axios.get('/api/v1/polls/')
            .then(res => setPolls(res.data))
            .finally(() => setLoading(false));
    };

    useEffect(fetchPolls, []);
    
    const handleVote = (choiceId: number) => {
        axios.post(`/api/v1/polls/choices/${choiceId}/vote/`)
            .then(res => {
                // Обновляем состояние опросов, чтобы показать новые результаты
                fetchPolls();
            })
            .catch(err => console.error("Voting error:", err));
    };

    if (loading) return <Box display="flex" justifyContent="center" mt={4}><CircularProgress /></Box>;

    return (
        <Container maxWidth="md" sx={{ py: 4 }}>
            <Typography variant="h4" component="h1" gutterBottom color="primary">
                Опросы и голосования
            </Typography>
            {polls.length === 0 ? (
                <Typography>Активных опросов нет.</Typography>
            ) : (
                polls.map(poll => (
                    <Paper key={poll.id} elevation={2} sx={{ mb: 2 }}>
                        <Accordion>
                            <AccordionSummary expandIcon={<ExpandMoreIcon />}>
                                <Typography variant="h6">{poll.title}</Typography>
                            </AccordionSummary>
                            <AccordionDetails>
                                <Typography paragraph>{poll.description}</Typography>
                                {poll.questions.map((q: any) => (
                                    <div key={q.id}>
                                        <Typography variant="subtitle1" sx={{ mt: 1, fontWeight: 'bold' }}>{q.text}</Typography>
                                        {/* Здесь будет логика голосования */}
                                        {q.choices.map((c: any) => (
                                             <Box key={c.id} display="flex" justifyContent="space-between" alignItems="center">
                                                <Typography>{c.text}</Typography>
                                                <Box display="flex" alignItems="center">
                                                    <Typography sx={{ mr: 2 }}>{c.votes} голосов</Typography>
                                                    <Button size="small" variant="outlined" onClick={() => handleVote(c.id)}>Голосовать</Button>
                                                </Box>
                                             </Box>
                                        ))}
                                    </div>
                                ))}
                            </AccordionDetails>
                        </Accordion>
                    </Paper>
                ))
            )}
        </Container>
    );
};