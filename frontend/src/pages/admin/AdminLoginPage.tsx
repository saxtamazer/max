import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";

export function AdminLoginPage() {
    const navigate = useNavigate();
    const { login } = useAuth();

    const [username, setUsername] = useState<string>("");
    const [password, setPassword] = useState<string>("");
    const [error, setError] = useState<string>("");

    const handleSubmit = () => {
        setError("");
        if (!username || !password) {
            setError("Заполните все поля");
            return;
        }

        const success = login(username, password);

        if (success) {
            navigate("/admin/upload")
        } else {
            setError("Неверный логин или пароль")
        }
    };

    const handleKeyDown = (e: React.KeyboardEvent) => {
        if (e.key === "Enter") handleSubmit();
    };

    return (
    // Внешний контейнер — занимает весь экран, центрирует карточку
    // bg-neutral-950 — почти чёрный фон
    // flex items-center justify-center — центрирование по обеим осям
    <div className="min-h-screen bg-neutral-950 flex items-center justify-center">

      {/* Карточка формы */}
      {/* border border-neutral-800 — едва заметная граница, даёт объём */}
      <div className="w-full max-w-sm bg-neutral-900 border border-neutral-800 rounded-lg p-8">

        {/* Заголовок */}
        <div className="mb-8">
          <h1 className="text-white text-2xl font-semibold tracking-tight">
            Панель управления
          </h1>
          <p className="text-neutral-500 text-sm mt-1">
            Войдите чтобы продолжить
          </p>
        </div>

        {/* Поля формы */}
        <div className="flex flex-col gap-4">

          {/* Поле логина */}
          {/* group — позволяет стилизовать дочерние элементы при hover на родителе */}
          <div className="flex flex-col gap-1">
            <label className="text-neutral-400 text-xs uppercase tracking-widest">
              Логин
            </label>
            <input
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              onKeyDown={handleKeyDown}
              placeholder="admin"
              // focus:outline-none focus:ring-1 — убираем дефолтный outline
              // и добавляем свой тонкий ring при фокусе
              className="bg-neutral-800 text-white text-sm rounded px-3 py-2.5
                         border border-neutral-700
                         focus:outline-none focus:ring-1 focus:ring-white
                         placeholder:text-neutral-600
                         transition-all duration-200"
            />
          </div>

          {/* Поле пароля */}
          <div className="flex flex-col gap-1">
            <label className="text-neutral-400 text-xs uppercase tracking-widest">
              Пароль
            </label>
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              onKeyDown={handleKeyDown}
              placeholder="••••••••"
              className="bg-neutral-800 text-white text-sm rounded px-3 py-2.5
                         border border-neutral-700
                         focus:outline-none focus:ring-1 focus:ring-white
                         placeholder:text-neutral-600
                         transition-all duration-200"
            />
          </div>

          {/* Сообщение об ошибке — рендерится только если error не пустая строка */}
          {error && (
            <p className="text-red-400 text-sm">{error}</p>
          )}

          {/* Кнопка входа */}
          <button
            onClick={handleSubmit}
            // hover:bg-neutral-200 — чуть темнее при наведении
            // active:scale-95 — лёгкое нажатие при клике
            className="mt-2 w-full bg-white text-neutral-900 text-sm font-medium
                       rounded py-2.5
                       hover:bg-neutral-200
                       active:scale-95
                       transition-all duration-200"
          >
            Войти
          </button>

        </div>
      </div>
    </div>
  );
};