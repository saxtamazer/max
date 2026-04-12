// src/App.tsx
import React from 'react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { CssBaseline, ThemeProvider } from '@mui/material';
import { theme } from './theme'; // Наша новая тема
import { MainLayout } from './layouts/MainLayout'; // Наш новый лейаут
import { ArticleListPage } from './pages/ArticleListPage';
import { ArticleDetailPage } from './pages/ArticleDetailPage';
import { SchedulePage } from './pages/SchedulePage';
import { PollsPage } from './pages/PollsPage';
import { ManageSchedulePage } from './pages/ManageSchedulePage';

// Админка
import { AuthProvider } from './context/AuthContext';
import { ProtectedRoute } from './components/admin/ProtectedRoute';
import { AdminLoginPage } from './pages/admin/AdminLoginPage'
import { AdminLayout } from './layouts/AdminLayout'
import { UploadPage } from './pages/admin/UploadPage'
import { WorkloadPage } from './pages/admin/WorkloadPage'

function App() {
  return (
    <AuthProvider>
      <ThemeProvider theme={theme}>
        <CssBaseline />
        <BrowserRouter>
          {/* ИСПРАВЛЕНИЕ: Все <Route> теперь находятся внутри <Routes> */}
          <Routes>
          {/* 
            Этот Route с элементом MainLayout действует как "обертка".
            Все вложенные в него Route будут отрендерены внутри MainLayout.
          */}
            <Route path="/" element={<MainLayout />}>
              {/* `index` означает, что этот компонент будет показан для пути "/" */}
              <Route index element={<ArticleListPage />} /> 
              <Route path="schedule" element={<SchedulePage />} />
              <Route path="polls" element={<PollsPage />} />
              <Route path="article/:id" element={<ArticleDetailPage />} />
              <Route path="manage/schedule" element={<ManageSchedulePage />} />
            </Route>

            <Route path="/admin/login" element={<AdminLoginPage />} />

            <Route element={<ProtectedRoute />}>
              <Route path = "/admin" element = { <AdminLayout/> }>
                <Route path = "upload" element = { <UploadPage/> }/>
                <Route path = "workload" element = { <WorkloadPage/> }/>
              </Route>
            </Route>
          </Routes>
        </BrowserRouter>
      </ThemeProvider>
    </AuthProvider>
  );
}

export default App;