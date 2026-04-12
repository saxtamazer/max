// src/components/admin/workload/WorkloadSearch.tsx
import { useState, useEffect, useRef } from "react";
import { WorkloadType, AnySlot } from "../../../types/workload";

interface Props {
  type: WorkloadType;
  onDataLoaded: (name: string, data: AnySlot[]) => void;
}

export function WorkloadSearch({ type, onDataLoaded }: Props) {
  // Текст в поле ввода
  const [query, setQuery] = useState<string>("");

  // Полный список объектов загруженный с сервера (все группы / все преподаватели / все аудитории)
  const [allItems, setAllItems] = useState<string[]>([]);

  // Отфильтрованные варианты для автодополнения
  const [suggestions, setSuggestions] = useState<string[]>([]);

  // Показывать ли выпадающий список
  const [isOpen, setIsOpen] = useState<boolean>(false);

  // Идёт ли загрузка данных нагрузки (после выбора объекта)
  const [isLoading, setIsLoading] = useState<boolean>(false);

  // Ref на контейнер — нужен чтобы закрывать список при клике вне него
  const containerRef = useRef<HTMLDivElement>(null);

  // Маппинг типа на эндпоинт для получения списка всех объектов
  const listEndpoint: Record<WorkloadType, string> = {
    group:      "/api/v1/load/group",
    educator:   "/api/v1/load/educator",
    auditorium: "/api/v1/load/auditorium",
  };

  // Маппинг типа на эндпоинт для получения нагрузки конкретного объекта
  const dataEndpoint: Record<WorkloadType, string> = {
    group:      "/api/v1/load/group",
    educator:   "/api/v1/load/educator",
    auditorium: "/api/v1/load/auditorium",
  };

  // Загружаем список всех объектов когда меняется тип.
  // useEffect с зависимостью [type] — выполняется при монтировании
  // и каждый раз когда type меняется (группа → преподаватель и т.д.)
  useEffect(() => {
    setQuery("");
    setSuggestions([]);
    setAllItems([]);

    const fetchList = async () => {
      try {
        const response = await fetch(listEndpoint[type]);
        if (!response.ok) return;
        const data: string[] = await response.json();
        setAllItems(data);
      } catch {
        // Если сервер недоступен — просто оставляем пустой список
      }
    };

    fetchList();
  }, [type]);

  // Фильтруем список локально при каждом изменении query.
  // Никаких запросов к серверу — всё уже загружено в allItems.
  useEffect(() => {
    if (!query.trim()) {
      setSuggestions([]);
      setIsOpen(false);
      return;
    }

    const filtered = allItems.filter((item) =>
      item.toLowerCase().includes(query.toLowerCase())
    );

    setSuggestions(filtered.slice(0, 8)); // показываем максимум 8 вариантов
    setIsOpen(filtered.length > 0);
  }, [query, allItems]);

  // Закрываем список при клике вне компонента
  useEffect(() => {
    const handleClickOutside = (e: MouseEvent) => {
      if (containerRef.current && !containerRef.current.contains(e.target as Node)) {
        setIsOpen(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    // Возвращаем функцию очистки — она вызовется при размонтировании компонента.
    // Без этого обработчик останется висеть в памяти после удаления компонента.
    return () => document.removeEventListener("mousedown", handleClickOutside);
  }, []);

  // Пользователь выбрал вариант из списка — загружаем нагрузку
  const handleSelect = async (name: string) => {
    setQuery(name);
    setIsOpen(false);
    setIsLoading(true);

    try {
      const response = await fetch(`${dataEndpoint[type]}/${encodeURIComponent(name)}`);
      if (!response.ok) return;
      const data: AnySlot[] = await response.json();
      // Передаём данные наверх в WorkloadPage через callback
      onDataLoaded(name, data);
    } catch {
      // Обработка ошибки сети
    } finally {
      // finally выполняется всегда — и при успехе и при ошибке.
      // Гарантирует что спиннер исчезнет в любом случае.
      setIsLoading(false);
    }
  };

  const placeholders: Record<WorkloadType, string> = {
    group:      "Например: ИС-301",
    educator:   "Например: Иванов И.И.",
    auditorium: "Например: А-301",
  };

  return (
    // relative — нужен чтобы выпадающий список позиционировался
    // относительно этого контейнера, а не всей страницы
    <div ref={containerRef} className="relative flex flex-col gap-1">
      <label className="text-neutral-400 text-xs uppercase tracking-widest">
        Поиск
      </label>

      <div className="relative">
        <input
          type="text"
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          onFocus={() => suggestions.length > 0 && setIsOpen(true)}
          placeholder={placeholders[type]}
          className="w-full bg-neutral-800 text-white text-sm rounded px-3 py-2.5
                     border border-neutral-700
                     focus:outline-none focus:ring-1 focus:ring-white
                     placeholder:text-neutral-600
                     transition-all duration-200"
        />

        {/* Спиннер загрузки — показывается пока идёт запрос нагрузки */}
        {isLoading && (
          <div className="absolute right-3 top-1/2 -translate-y-1/2">
            <div className="w-4 h-4 border-2 border-neutral-600
                            border-t-white rounded-full animate-spin" />
          </div>
        )}
      </div>

      {/* Выпадающий список вариантов */}
      {/* absolute — выпадает поверх контента, не сдвигая остальное */}
      {isOpen && (
        <ul className="absolute top-full left-0 right-0 mt-1 z-10
                       bg-neutral-800 border border-neutral-700 rounded-lg
                       overflow-hidden shadow-xl">
          {suggestions.map((item) => (
            <li
              key={item}
              // onMouseDown вместо onClick — важный нюанс:
              // onClick срабатывает после onBlur на input,
              // из-за чего список успевает закрыться раньше клика.
              // onMouseDown срабатывает раньше onBlur.
              onMouseDown={() => handleSelect(item)}
              className="px-3 py-2.5 text-sm text-neutral-300
                         hover:bg-neutral-700 hover:text-white
                         cursor-pointer transition-colors duration-100"
            >
              {item}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}