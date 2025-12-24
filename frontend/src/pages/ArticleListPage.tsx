// src/pages/ArticleListPage.tsx
import React, { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import { Link as RouterLink } from 'react-router-dom';
import { 
    Container, 
    Typography, 
    CircularProgress, 
    Box, 
    Grid, 
    Card, 
    CardActionArea,
    CardContent, 
    CardMedia,
    TextField,
    Chip,
    Stack,
    InputAdornment // <<< ИМПОРТИРУЕМ InputAdornment
} from '@mui/material';
import SearchIcon from '@mui/icons-material/Search';

// --- Описываем новые интерфейсы ---
interface ArticleListItem {
    id: number;
    title: string;
    created_at: string;
    main_image: string | null;
    category_name: string | null;
}

interface Category {
    id: number;
    name: string;
    slug: string;
}
// ------------------------------------

export const ArticleListPage: React.FC = () => {
    const [articles, setArticles] = useState<ArticleListItem[]>([]);
    const [categories, setCategories] = useState<Category[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [searchTerm, setSearchTerm] = useState('');
    const [selectedCategory, setSelectedCategory] = useState<string | null>(null);

    const fetchData = useCallback(() => {
        setLoading(true);
        const params = {
            search: searchTerm,
            category: selectedCategory,
        };
        
        const articlesPromise = axios.get('/api/v1/kb/articles/', { params });
        const categoriesPromise = axios.get('/api/v1/kb/categories/');

        Promise.all([articlesPromise, categoriesPromise])
            .then(([articlesRes, categoriesRes]) => {
                setArticles(articlesRes.data);
                setCategories(categoriesRes.data);
            })
            .catch(error => {
                console.error("There was an error fetching data!", error);
            })
            .finally(() => {
                setLoading(false);
            });
    }, [searchTerm, selectedCategory]);

    useEffect(() => {
        fetchData();
    }, [fetchData]);

    return (
        <Container maxWidth="lg" sx={{ py: 4 }}>
            <Typography variant="h4" component="h1" gutterBottom color="primary">
                База знаний
            </Typography>
            
            {/* --- Блок поиска и фильтров --- */}
            <Box sx={{ mb: 4 }}>
                <TextField
                    fullWidth
                    variant="outlined"
                    label="Поиск по статьям..."
                    // ИСПРАВЛЕНИЕ 1: Добавляем тип для 'e'
                    onChange={(e: React.ChangeEvent<HTMLInputElement>) => setSearchTerm(e.target.value)}
                    InputProps={{
                      // ИСПРАВЛЕНИЕ 2: Оборачиваем иконку в InputAdornment
                      startAdornment: (
                        <InputAdornment position="start">
                          <SearchIcon />
                        </InputAdornment>
                      )
                    }}
                />
                <Stack direction="row" spacing={1} sx={{ mt: 2, flexWrap: 'wrap', gap: 1 }}>
                    <Chip
                        label="Все категории"
                        onClick={() => setSelectedCategory(null)}
                        variant={selectedCategory === null ? "filled" : "outlined"}
                        color="primary"
                    />
                    {categories.map(cat => (
                        <Chip
                            key={cat.id}
                            label={cat.name}
                            onClick={() => setSelectedCategory(cat.slug)}
                            variant={selectedCategory === cat.slug ? "filled" : "outlined"}
                        />
                    ))}
                </Stack>
            </Box>

            {/* --- Отображение статей или загрузчика --- */}
            {loading ? (
                <Box display="flex" justifyContent="center" alignItems="center" minHeight="50vh">
                    <CircularProgress />
                </Box>
            ) : (
                <Grid container spacing={3}>
                    {articles.length > 0 ? articles.map(article => (
                        <Grid item xs={12} sm={6} md={4} key={article.id}>
                            <Card sx={{ height: '100%', display: 'flex', flexDirection: 'column' }}>
                                <CardActionArea component={RouterLink} to={`/article/${article.id}`} sx={{ flexGrow: 1 }}>
                                    <CardMedia
                                        component="img"
                                        height="160"
                                        image={article.main_image || '/placeholder.png'} // Заглушка, если нет картинки
                                        alt={article.title}
                                    />
                                    <CardContent>
                                        <Typography gutterBottom variant="h6" component="div">
                                            {article.title}
                                        </Typography>
                                        <Stack direction="row" justifyContent="space-between" alignItems="center" sx={{ mt: 1 }}>
                                             <Typography variant="body2" color="text.secondary">
                                                {new Date(article.created_at).toLocaleDateString()}
                                            </Typography>
                                            {article.category_name && (
                                                <Chip label={article.category_name} size="small" />
                                            )}
                                        </Stack>
                                    </CardContent>
                                </CardActionArea>
                            </Card>
                        </Grid>
                    )) : (
                        <Grid item xs={12}>
                           <Typography sx={{textAlign: 'center', mt: 4}}>
                                Статьи, соответствующие вашему запросу, не найдены.
                           </Typography>
                        </Grid>
                    )}
                </Grid>
            )}
        </Container>
    );
};