import { useState } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";

type ValuePiece = Date | null;
type Value = ValuePiece | [ValuePiece, ValuePiece];

export default function MyCalendar() {
    const [value, setValue] = useState<Value>(new Date());

    return (
        <div className="p-6 flex flex-col items-center">
            <h2 className="text-xl font-bold mb-4">Sélectionnez une date</h2>
            <Calendar
                onChange={setValue}
                value={value}
                locale="fr-FR"
            />

            <p className="mt-4 text-gray-700">
                📅 Date choisie :{" "}
                <span className="font-semibold">
          {Array.isArray(value)
              ? `${value[0]?.toLocaleDateString()} - ${value[1]?.toLocaleDateString()}`
              : value?.toLocaleDateString()}
        </span>
            </p>
        </div>
    );
}
