// src/pages/ArticleDetailPage.tsx
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams, Link as RouterLink } from 'react-router-dom';
import { Container, Typography, Paper, CircularProgress, Box, Button, Chip } from '@mui/material';

interface ArticleDetail {
    id: number;
    title: string;
    content: string;
    main_image: string | null; // <-- Добавили поле
    category_name: string | null; // <-- Добавили поле
}

export const ArticleDetailPage: React.FC = () => {
    const [article, setArticle] = useState<ArticleDetail | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const { id } = useParams<{ id: string }>();

    useEffect(() => {
        if (id) {
            axios.get(`/api/v1/kb/articles/${id}/`)
                .then(response => {
                    setArticle(response.data);
                    setLoading(false);
                })
                .catch(error => {
                    console.error(`There was an error fetching the article with id ${id}!`, error);
                    setLoading(false);
                });
        }
    }, [id]);

    if (loading) {
        return (
            <Box display="flex" justifyContent="center" alignItems="center" minHeight="80vh">
                <CircularProgress />
            </Box>
        );
    }

    if (!article) {
        return (
            <Container maxWidth="md" sx={{ py: 4, textAlign: 'center' }}>
                <Typography variant="h5">Статья не найдена.</Typography>
                <Button component={RouterLink} to="/" variant="contained" sx={{ mt: 2 }}>
                    Вернуться к списку
                </Button>
            </Container>
        );
    }

    return (
        <Container maxWidth="md" sx={{ py: 4 }}>
            <Button component={RouterLink} to="/" variant="outlined" sx={{ mb: 2 }}>
                &larr; Назад к списку
            </Button>
            <Paper elevation={3}>
                {/* --- ОТОБРАЖЕНИЕ ГЛАВНОГО ИЗОБРАЖЕНИЯ --- */}
                {article.main_image && (
                    <Box
                        component="img"
                        src={article.main_image}
                        alt={article.title}
                        sx={{
                            width: '100%',
                            height: {xs: 200, sm: 300},
                            objectFit: 'cover',
                            // Скругляем верхние углы под стиль Paper
                            borderTopLeftRadius: 'inherit',
                            borderTopRightRadius: 'inherit'
                        }}
                    />
                )}
                {/* --- КОНЕЦ БЛОКА ИЗОБРАЖЕНИЯ --- */}

                <Box sx={{ p: { xs: 2, sm: 3, md: 4 } }}>
                    {article.category_name && <Chip label={article.category_name} color="primary" sx={{ mb: 2 }} />}
                    <Typography variant="h4" component="h1" gutterBottom>
                        {article.title}
                    </Typography>
                    <Typography component="div" sx={{ 
                        mt: 2, 
                        '& p': { mb: 1.5, lineHeight: 1.7 },
                        '& h1, & h2, & h3': { mt: 2.5, mb: 1, fontWeight: 'bold' },
                        '& a': { color: 'primary.main', textDecoration: 'underline' },
                        '& ul, & ol': { pl: 2.5 },
                        '& blockquote': { borderLeft: 4, borderColor: 'grey.300', pl: 2, fontStyle: 'italic', color: 'text.secondary' },
                        // Стили для картинок в контенте
                        '& img': { maxWidth: '100%', height: 'auto', borderRadius: 2, my: 2 }
                    }}>
                        <div dangerouslySetInnerHTML={{ __html: article.content }} />
                    </Typography>
                </Box>
            </Paper>
        </Container>
    );
};