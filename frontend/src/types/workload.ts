// src/types/workload.ts

export type WorkloadType = "group" | "educator" | "auditorium";

// Внимательно смотрим на @JsonProperty в Java коде —
// именно эти имена придут в JSON ответе от сервера.

// GET /api/v1/load/auditorium/{name}
// Заметь: здесь поле называется is_even (есть @JsonProperty("is_even"))
export interface AuditoriumSlot {
  is_even: boolean;      // @JsonProperty("is_even")
  day_of_week: number;
  pair_number: number;
  groups: string[];
}

// GET /api/v1/load/educator/{name}
// Здесь поле even БЕЗ @JsonProperty — значит в JSON придёт как "even"
export interface EducatorSlot {
  even: boolean;         // без @JsonProperty — имя совпадает с полем
  day_of_week: number;
  pair_number: number;
  pair_type: string;
  pair_name: string;
  groups: string[];
  auditoriums: string[];
}

// GET /api/v1/load/group/{name}
export interface GroupSlot {
  even: boolean;
  day_of_week: number;
  pair_number: number;
  pair_name: string;
  pair_type: string;
  auditoriums: string[];
}

export type AnySlot = GroupSlot | EducatorSlot | AuditoriumSlot;

// Вспомогательная функция — TypeScript "type guard".
// Позволяет понять какой именно тип слота у нас в руках.
// Используется в компонентах когда надо получить поле
// которое есть не у всех трёх типов (например pair_name).
// "slot is GroupSlot" — говорит TypeScript:
// "если функция вернула true — считай что slot это GroupSlot"
export function isGroupSlot(slot: AnySlot): slot is GroupSlot {
  return "auditoriums" in slot && "pair_name" in slot && !("groups" in slot);
}

export function isEducatorSlot(slot: AnySlot): slot is EducatorSlot {
  return "groups" in slot && "auditoriums" in slot;
}

export function isAuditoriumSlot(slot: AnySlot): slot is AuditoriumSlot {
  return "is_even" in slot;
}

// Данные для попапа — формируем на фронте из AnySlot
export interface PopupData {
  day_of_week: number;
  pair_number: number;
  pair_name?: string;
  pair_type?: string;
  auditoriums?: string[];
  groups?: string[];
}