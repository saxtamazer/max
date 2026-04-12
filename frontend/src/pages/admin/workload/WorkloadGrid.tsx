// src/components/admin/workload/WorkloadGrid.tsx
import { useState } from "react";
import {
  AnySlot, WorkloadType, PopupData,
  isGroupSlot, isEducatorSlot, isAuditoriumSlot
} from "../../../types/workload";
import { LessonPopup } from "./LessonPopup";

interface Props {
  slots: AnySlot[];
  isEven: boolean;
  type: WorkloadType;
}

// Константы вынесены за пределы компонента —
// они не меняются и не должны пересоздаваться при каждом рендере
const DAYS = ["Пн", "Вт", "Ср", "Чт", "Пт"];
const PAIRS = [1, 2, 3, 4, 5, 6];

export function WorkloadGrid({ slots, isEven, type }: Props) {
  // Данные для попапа. null — попап закрыт.
  const [popup, setPopup] = useState<PopupData | null>(null);

  // Фильтруем слоты по чётности — именно здесь происходит
  // фильтрация которую мы заложили в архитектуру:
  // данные загружены все сразу, фильтруем на фронте без запросов к серверу.
  // Для AuditoriumSlot поле называется is_even, для остальных — even.
  const filteredSlots = slots.filter((slot) => {
    if (isAuditoriumSlot(slot)) return slot.is_even === isEven;
    return slot.even === isEven;
  });

  // Ищем слот для конкретной ячейки (день + номер пары).
  // day: 1=Пн, 2=Вт, ..., 5=Пт — соответствует индексу в DAYS + 1
  const findSlot = (day: number, pair: number): AnySlot | null => {
    return filteredSlots.find(
      (s) => s.day_of_week === day && s.pair_number === pair
    ) ?? null;
    // ?? null — оператор "nullish coalescing":
    // если find вернул undefined — заменяем на null
  };

  // Формируем данные для попапа из слота.
  // Используем type guards чтобы безопасно обратиться к полям
  // которые есть не у всех типов слотов.
  const buildPopupData = (slot: AnySlot, day: number, pair: number): PopupData => {
    const base: PopupData = {
      day_of_week: day,
      pair_number: pair,
    };

    if (isGroupSlot(slot)) {
      return { ...base, pair_name: slot.pair_name, pair_type: slot.pair_type, auditoriums: slot.auditoriums };
    }
    if (isEducatorSlot(slot)) {
      return { ...base, pair_name: slot.pair_name, pair_type: slot.pair_type, groups: slot.groups, auditoriums: slot.auditoriums };
    }
    if (isAuditoriumSlot(slot)) {
      return { ...base, groups: slot.groups };
    }

    return base;
  };

  const handleCellClick = (day: number, pair: number) => {
    const slot = findSlot(day, pair);
    if (!slot) return; // на пустые ячейки не реагируем
    setPopup(buildPopupData(slot, day, pair));
  };

  return (
    // relative — нужен для позиционирования попапа
    <div className="relative">

      {/* Сетка */}
      <div className="overflow-x-auto">
        <table className="w-full border-collapse">

          {/* Заголовок — дни недели */}
          <thead>
            <tr>
              {/* Пустая ячейка над номерами пар */}
              <th className="w-16 pb-3" />
              {DAYS.map((day, idx) => (
                <th
                  key={day}
                  className="pb-3 text-neutral-400 text-xs font-medium
                             uppercase tracking-widest text-center"
                >
                  {day}
                </th>
              ))}
            </tr>
          </thead>

          <tbody>
            {PAIRS.map((pair) => (
              <tr key={pair}>
                {/* Номер пары */}
                <td className="pr-4 py-1.5 text-neutral-600 text-xs
                               font-medium text-right align-middle w-16">
                  {pair}
                </td>

                {/* Ячейки для каждого дня */}
                {DAYS.map((_, dayIdx) => {
                  const day = dayIdx + 1; // 1=Пн, 2=Вт...
                  const slot = findSlot(day, pair);
                  const isBusy = slot !== null;

                  return (
                    <td key={day} className="p-1 text-center">
                      <div
                        onClick={() => handleCellClick(day, pair)}
                        className={`
                          w-full h-10 rounded transition-all duration-150
                          ${isBusy
                            ? "bg-red-500/20 border border-red-500/40 cursor-pointer hover:bg-red-500/30"
                            : "bg-neutral-900 border border-neutral-800 cursor-default"
                          }
                        `}
                      />
                    </td>
                  );
                })}
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {/* Попап — рендерится поверх сетки когда popup !== null */}
      {popup && (
        <LessonPopup
          data={popup}
          type={type}
          onClose={() => setPopup(null)}
        />
      )}

    </div>
  );
}