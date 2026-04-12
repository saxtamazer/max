// src/components/admin/workload/LessonPopup.tsx
import { PopupData, WorkloadType } from "../../../types/workload";

interface Props {
  data: PopupData;
  type: WorkloadType;
  onClose: () => void;
}

const DAY_NAMES: Record<number, string> = {
  1: "Понедельник",
  2: "Вторник",
  3: "Среда",
  4: "Четверг",
  5: "Пятница",
};

export function LessonPopup({ data, type, onClose }: Props) {
  return (
    // Затемнённый фон — клик по нему закрывает попап
    <div
      className="fixed inset-0 z-20 flex items-center justify-center
                 bg-black/60 backdrop-blur-sm"
      onClick={onClose}
    >
      {/* Карточка — stopPropagation не даёт клику по карточке
          "протечь" к фону и закрыть попап */}
      <div
        className="bg-neutral-900 border border-neutral-700 rounded-lg
                   p-6 w-full max-w-sm shadow-2xl"
        onClick={(e) => e.stopPropagation()}
      >
        {/* Заголовок */}
        <div className="flex items-start justify-between mb-4">
          <div>
            <p className="text-neutral-500 text-xs uppercase tracking-widest mb-1">
              {DAY_NAMES[data.day_of_week]} · Пара {data.pair_number}
            </p>
            {data.pair_name && (
              <h3 className="text-white font-medium text-base">
                {data.pair_name}
              </h3>
            )}
            {data.pair_type && (
              <p className="text-neutral-400 text-sm mt-0.5">
                {data.pair_type}
              </p>
            )}
          </div>

          <button
            onClick={onClose}
            className="text-neutral-600 hover:text-white
                       transition-colors duration-150 text-lg leading-none"
          >
            ✕
          </button>
        </div>

        {/* Детали */}
        <div className="flex flex-col gap-3">

          {/* Аудитории — показываем для группы и преподавателя */}
          {data.auditoriums && data.auditoriums.length > 0 && (
            <div>
              <p className="text-neutral-500 text-xs uppercase tracking-widest mb-1">
                Аудитории
              </p>
              <div className="flex flex-wrap gap-1.5">
                {data.auditoriums.map((a) => (
                  <span
                    key={a}
                    className="bg-neutral-800 text-neutral-300 text-xs
                               px-2 py-1 rounded"
                  >
                    {a}
                  </span>
                ))}
              </div>
            </div>
          )}

          {/* Группы — показываем для преподавателя и аудитории */}
          {data.groups && data.groups.length > 0 && (
            <div>
              <p className="text-neutral-500 text-xs uppercase tracking-widest mb-1">
                Группы
              </p>
              <div className="flex flex-wrap gap-1.5">
                {data.groups.map((g) => (
                  <span
                    key={g}
                    className="bg-neutral-800 text-neutral-300 text-xs
                               px-2 py-1 rounded"
                  >
                    {g}
                  </span>
                ))}
              </div>
            </div>
          )}

        </div>
      </div>
    </div>
  );
}