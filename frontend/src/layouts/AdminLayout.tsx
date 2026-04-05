import {Outlet, NavLink, useNavigate} from "react-router-dom";
import { useAuth } from "../context/AuthContext";

interface NavItem {
    label: string;
    path: string;
    icon: string;
}

const navItems: NavItem[] = [
    {
      label: "Загрузка расписания",
      path: "/admin/upload",
      icon: "📂"
    },
    {
      label: "Нагрузка",
      path: "/admin/workload",
      icon: "📊"
    }
];

export function AdminLayout() {
    const {logout} = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate("/admin/login", { replace: true });
    };

    return (
    // Весь экран, тёмный фон, flex по горизонтали
    <div className="min-h-screen bg-neutral-950 flex">

      {/* ===== САЙДБАР ===== */}
      {/* w-64 — фиксированная ширина 256px */}
      {/* shrink-0 — запрещаем сайдбару сжиматься */}
      <aside className="w-64 shrink-0 bg-neutral-900 border-r border-neutral-800
                        flex flex-col">

        {/* Логотип / название */}
        <div className="px-6 py-5 border-b border-neutral-800">
          <h1 className="text-white font-semibold text-base tracking-tight">
            Админ панель
          </h1>
          <p className="text-neutral-500 text-xs mt-0.5">
            Управление расписанием
          </p>
        </div>

        {/* Навигация */}
        {/* flex-1 — растягивает nav на всю доступную высоту */}
        {/* это "толкает" кнопку выхода вниз */}
        <nav className="flex-1 px-3 py-4 flex flex-col gap-1">
          {navItems.map((item) => (
            // NavLink — как обычный Link, но автоматически добавляет
            // класс active когда URL совпадает с path.
            // Мы используем это чтобы подсветить активный пункт.
            <NavLink
              key={item.path}
              to={item.path}
              // className принимает функцию — React Router передаёт
              // объект { isActive } чтобы мы могли менять стиль
              className={({ isActive }) =>
                `flex items-center gap-3 px-3 py-2.5 rounded text-sm
                 transition-colors duration-150
                 ${isActive
                   ? "bg-white text-neutral-900 font-medium"
                   : "text-neutral-400 hover:text-white hover:bg-neutral-800"
                 }`
              }
            >
              <span>{item.icon}</span>
              <span>{item.label}</span>
            </NavLink>
          ))}
        </nav>

        {/* Кнопка выхода — прижата к низу сайдбара */}
        <div className="px-3 py-4 border-t border-neutral-800">
          <button
            onClick={handleLogout}
            className="w-full flex items-center gap-3 px-3 py-2.5 rounded text-sm
                       text-neutral-400 hover:text-white hover:bg-neutral-800
                       transition-colors duration-150"
          >
            <span>🚪</span>
            <span>Выйти</span>
          </button>
        </div>

      </aside>

      {/* ===== ГЛАВНАЯ ОБЛАСТЬ ===== */}
      {/* flex-1 — занимает всё оставшееся место справа от сайдбара */}
      <main className="flex-1 overflow-auto">

        {/* Шапка */}
        <header className="border-b border-neutral-800 px-8 py-4">
          <p className="text-neutral-500 text-sm">
            Университет — Система управления расписанием
          </p>
        </header>

        {/* Контент — здесь рендерится выбранный инструмент */}
        <div className="px-8 py-6">
          {/* Outlet — это "дырка" куда React Router вставит дочернюю страницу.
              Например /admin/upload вставит сюда компонент UploadPage */}
          <Outlet />
        </div>

      </main>

    </div>
  );
};