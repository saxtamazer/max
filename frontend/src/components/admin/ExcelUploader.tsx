import { useState, useRef, DragEvent, ChangeEvent } from "react";

type UploadStatus = "idle" | "loading" | "success" | "error";

export function ExcelUploader() {
    const [file, setFile] = useState <File | null> (null);
    const [status, setStatus] = useState<UploadStatus>("idle");
    const [message, setMessage] = useState<string>("");
    const [isDragging, setIsDragging] = useState<boolean>(false);

    const inputRef = useRef<HTMLInputElement>(null);

    const validateFile = (f: File): boolean => {
        const allowed =[
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xlsx
            "application/vnd.ms-excel", // .xls
        ];
        return allowed.includes(f.type);
    };

    const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
        const selected = e.target.files?.[0];
        if (!selected) return;

        if (!validateFile(selected)) {
            setMessage("Допускаются только файлы .xlsx и .xls")
            setStatus("error");
            return;
        }

        setFile(selected);
        setStatus("idle");
        setMessage("");
    };

    // Dragging file
    const handleDrop = (e: DragEvent<HTMLDivElement>) => {
        e.preventDefault();
        setIsDragging(false);

        const dropped = e.dataTransfer.files?.[0];
        if (!dropped) return;

        if (!validateFile(dropped)) {
            setMessage("Допускаются только файлы .xlsx и .xls");
            setStatus("error");
            return;
        }

        setFile(dropped);
        setStatus("idle");
        setMessage("");
    };

    const handleDragOver = (e: DragEvent<HTMLDivElement>) => {
        e.preventDefault();
        setIsDragging(true);
    };

    const handleDragLeave = (e: DragEvent<HTMLDivElement>) => {
        e.preventDefault();
        setIsDragging(true);
    }

    const handleUpload = async() => {
        if (!file) return;

        setStatus("loading");
        setMessage("");

        const formData = new FormData();
        formData.append("file", file);

        try {
            const response = await fetch("http://localhost:8081/api/v1/parser/excel", {
                method: "POST",
                body: formData
            })

            if (response.ok) {
                setStatus("success");
                setMessage("Расписание успешно загружено в базу данных");
                setFile(null);
                if (inputRef.current) inputRef.current.value = "";
            } else {
                const errorText = await response.text();
                setStatus("error");
                setMessage(errorText || 'Ошибка сервера: ${response.status}')
            }
        } catch (err) {
            setStatus("error");
            setMessage("Не удалось подключиться к серверу");
        }
    };

    return (
    <div className="flex flex-col gap-4 max-w-xl">

      {/* Скрытый нативный input для выбора файла */}
      {/* Его не видно — мы управляем им через inputRef */}
      <input
        ref={inputRef}
        type="file"
        accept=".xlsx,.xls"
        onChange={handleFileChange}
        className="hidden"
      />

      {/* Зона drag-and-drop */}
      {/* Клик на неё → программно кликаем на скрытый input */}
      <div
        onClick={() => inputRef.current?.click()}
        onDrop={handleDrop}
        onDragOver={handleDragOver}
        onDragLeave={handleDragLeave}
        className={`
          border-2 border-dashed rounded-lg px-8 py-12
          flex flex-col items-center justify-center gap-3
          cursor-pointer transition-all duration-200
          ${isDragging
            ? "border-white bg-neutral-800"
            : "border-neutral-700 hover:border-neutral-500 hover:bg-neutral-900"
          }
        `}
      >
        <span className="text-4xl">📊</span>

        {/* Показываем имя файла если выбран, иначе инструкцию */}
        {file ? (
          <div className="text-center">
            <p className="text-white text-sm font-medium">{file.name}</p>
            <p className="text-neutral-500 text-xs mt-1">
              {(file.size / 1024).toFixed(1)} KB — нажмите чтобы заменить
            </p>
          </div>
        ) : (
          <div className="text-center">
            <p className="text-neutral-300 text-sm">
              Перетащите файл сюда или нажмите для выбора
            </p>
            <p className="text-neutral-600 text-xs mt-1">
              Поддерживаются .xlsx и .xls
            </p>
          </div>
        )}
      </div>

      {/* Сообщение об ошибке или успехе */}
      {message && (
        <p className={`text-sm ${
          status === "success" ? "text-green-400" : "text-red-400"
        }`}>
          {message}
        </p>
      )}

      {/* Кнопка загрузки */}
      {/* disabled если нет файла или идёт загрузка */}
      <button
        onClick={handleUpload}
        disabled={!file || status === "loading"}
        className={`
          px-6 py-2.5 rounded text-sm font-medium
          transition-all duration-200
          ${!file || status === "loading"
            ? "bg-neutral-800 text-neutral-600 cursor-not-allowed"
            : "bg-white text-neutral-900 hover:bg-neutral-200 active:scale-95"
          }
        `}
      >
        {status === "loading" ? "Загрузка..." : "Загрузить в базу данных"}
      </button>

    </div>
  );
}