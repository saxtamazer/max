export type WorkloadType = "group" | "educator" | "auditorium";

export interface GroupSlot {
    ecen: boolean;
    day_of_week: number;
    pair_number: number;
    auditorium: string[];
}

export interface EducatorSlot {
    even: boolean;
    day_of_week: number;
    pair_number: number;
    auditorium: string[];
    groups: string[]
}

export interface AuditoriumSlot {
    even: boolean;
    day_of_week: number;
    pair_number: number;
    groups: string[];
}

export type AnySlot = GroupSlot | EducatorSlot | AuditoriumSlot;

export interface GridCell {
    day_of_week: number;
    pair_number: number;
    slot: AnySlot | null;
}

export interface PopupData {
    day_of_week: number;
    pair_number: number;
    auditorium?:string[];
    groups?:string[];
}