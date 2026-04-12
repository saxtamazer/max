// src/pages/admin/WorkloadPage.tsx
import { useState } from "react";
import { WorkloadSearch } from "./workload/WorkloadSearch";
import { WorkloadGrid } from "./workload/WorkloadGrid";
import { WorkloadType, AnySlot } from "../../types/workload";

export function WorkloadPage() {
  // Какой тип объекта сейчас выбран: группа, преподаватель или аудитория
  const [activeType, setActiveType] = useState<WorkloadType>("group");

  // Имя выбранного объекта — например "ИС-301"
  // null означает что пользователь ещё ничего не выбрал
  const [selectedName, setSelectedName] = useState<string | null>(null);

  // Данные от сервера — массив всех слотов для выбранного объекта
  // (и чётные и нечётные сразу, фильтрация будет в WorkloadGrid)
  const [slots, setSlots] = useState<AnySlot[]>([]);

  // Чётная или нечётная неделя
  const [isEven, setIsEven] = useState<boolean>(true);

  // Эта функция передаётся в WorkloadSearch.
  // Когда пользователь выбирает объект из автодополнения —
  // Search вызывает её с именем и данными от сервера.
  const handleDataLoaded = (name: string, data: AnySlot[]) => {
    setSelectedName(name);
    setSlots(data);
  };

  // При смене типа (группа → преподаватель и т.д.) сбрасываем всё —
  // старые данные от предыдущего типа больше не актуальны
  const handleTypeChange = (type: WorkloadType) => {
    setActiveType(type);
    setSelectedName(null);
    setSlots([]);
  };

  // Конфигурация вкладок — аналогично navItems в сайдбаре:
  // данные отдельно от разметки
  const tabs: { type: WorkloadType; label: string }[] = [
    { type: "group",      label: "Группа"        },
    { type: "educator",   label: "Преподаватель"  },
    { type: "auditorium", label: "Аудитория"      },
  ];

  return (
    <div className="flex flex-col gap-6">

      {/* Заголовок */}
      <div>
        <h2 className="text-white text-xl font-semibold tracking-tight">
          Нагрузка
        </h2>
        <p className="text-neutral-500 text-sm mt-1">
          Анализ занятости групп, преподавателей и аудиторий
        </p>
      </div>

      {/* Панель управления */}
      <div className="flex flex-col gap-4">

        {/* Вкладки выбора типа */}
        <div className="flex gap-1 bg-neutral-900 border border-neutral-800
                        rounded-lg p-1 w-fit">
          {tabs.map((tab) => (
            <button
              key={tab.type}
              onClick={() => handleTypeChange(tab.type)}
              className={`
                px-4 py-1.5 rounded text-sm transition-all duration-150
                ${activeType === tab.type
                  ? "bg-white text-neutral-900 font-medium"
                  : "text-neutral-400 hover:text-white"
                }
              `}
            >
              {tab.label}
            </button>
          ))}
        </div>

        {/* Строка поиска + переключатель чётности */}
        {/* items-end — выравниваем по нижнему краю,
            чтобы кнопки были на одном уровне с полем поиска */}
        <div className="flex items-end gap-4">

          {/* Поиск с автодополнением */}
          {/* flex-1 — занимает всё свободное место */}
          <div className="flex-1 max-w-sm">
            <WorkloadSearch
              type={activeType}
              onDataLoaded={handleDataLoaded}
            />
          </div>

          {/* Переключатель чётная/нечётная */}
          <div className="flex gap-1 bg-neutral-900 border border-neutral-800
                          rounded-lg p-1">
            <button
              onClick={() => setIsEven(true)}
              className={`
                px-4 py-1.5 rounded text-sm transition-all duration-150
                ${isEven
                  ? "bg-white text-neutral-900 font-medium"
                  : "text-neutral-400 hover:text-white"
                }
              `}
            >
              Чётная
            </button>
            <button
              onClick={() => setIsEven(false)}
              className={`
                px-4 py-1.5 rounded text-sm transition-all duration-150
                ${!isEven
                  ? "bg-white text-neutral-900 font-medium"
                  : "text-neutral-400 hover:text-white"
                }
              `}
            >
              Нечётная
            </button>
          </div>

        </div>
      </div>

      {/* Сетка — показываем только если что-то выбрано */}
      {selectedName && slots.length > 0 ? (
        <WorkloadGrid
          slots={slots}
          isEven={isEven}
          type={activeType}
        />
      ) : (
        // Заглушка пока ничего не выбрано
        <div className="flex items-center justify-center h-48
                        border border-dashed border-neutral-800 rounded-lg">
          <p className="text-neutral-600 text-sm">
            Выберите объект для отображения нагрузки
          </p>
        </div>
      )}

    </div>
  );
}