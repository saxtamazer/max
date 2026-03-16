import { ExcelUploader } from "../../components/admin/ExcelUploader";

export function UploadPage() {
    return (
    <div className="flex flex-col gap-6">

      {/* Заголовок страницы */}
      <div>
        <h2 className="text-white text-xl font-semibold tracking-tight">
          Загрузка расписания
        </h2>
        <p className="text-neutral-500 text-sm mt-1">
          Загрузите Excel файл — данные автоматически запишутся в базу данных
        </p>
      </div>

      {/* Компонент загрузки */}
      <ExcelUploader />

    </div>
  );
}