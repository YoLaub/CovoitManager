import { useEffect, useState } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";

type Covoitureur = {
    id: number;
    nom: string;
    email: string;
    solde: number;
};

type Trajet = {
    id: number;
    lieuDepart?: string;
    lieuArrivee?: string;
    prix?: number;
};

export default function CalendarWithPresence() {
    const [date, setDate] = useState<Date | null>(null);
    const [showModal, setShowModal] = useState(false);
    const [covoitureurs, setCovoitureurs] = useState<Covoitureur[]>([]);
    const [trajets, setTrajets] = useState<Trajet[]>([]);

    // états séparés pour Aller et Retour
    const [selectedAllerIds, setSelectedAllerIds] = useState<number[]>([]);
    const [selectedRetourIds, setSelectedRetourIds] = useState<number[]>([]);

    // ids de trajet sélectionnés pour chaque sens (modifiable via <select>)
    const [trajetAllerId, setTrajetAllerId] = useState<number | null>(null);
    const [trajetRetourId, setTrajetRetourId] = useState<number | null>(null);

    useEffect(() => {
        // covoitureurs
        fetch("http://localhost:8080/api/covoiturage/covoitureur")
            .then((r) => r.json())
            .then(setCovoitureurs)
            .catch(console.error);

        // trajets (optionnel : récupère la liste des trajets pour selection)
        fetch("http://localhost:8080/api/covoiturage/trajet")
            .then((r) => r.json())
            .then((data: Trajet[]) => {
                setTrajets(data);
                if (data.length > 0) {
                    // valeurs par défaut si tu veux (1er trajet = aller, 2ème = retour)
                    setTrajetAllerId(data[1].id);
                    if (data.length > 1) setTrajetRetourId(data[2].id);
                }
            })
            .catch(console.error);
    }, []);

    const handleDayClick = (value: Date) => {
        setDate(value);
        setShowModal(true);
        setSelectedAllerIds([]);
        setSelectedRetourIds([]);
    };

    const toggleAller = (id: number) => {
        setSelectedAllerIds((prev) => (prev.includes(id) ? prev.filter((x) => x !== id) : [...prev, id]));
    };
    const toggleRetour = (id: number) => {
        setSelectedRetourIds((prev) => (prev.includes(id) ? prev.filter((x) => x !== id) : [...prev, id]));
    };

    const savePresence = async () => {
        if (!date) return;
        const dateStr = date.toLocaleDateString("fr-CA"); // yyyy-mm-dd

        // construire les présences à envoyer en une seule fois
        const payload: any[] = [];

        if (trajetAllerId) {
            for (const covId of selectedAllerIds) {
                payload.push({
                    date: dateStr,
                    present: true,
                    covoitureur: { id: covId },
                    trajet: { id: trajetAllerId },
                });
            }
        }

        if (trajetRetourId) {
            for (const covId of selectedRetourIds) {
                payload.push({
                    date: dateStr,
                    present: true,
                    covoitureur: { id: covId },
                    trajet: { id: trajetRetourId },
                });
            }
        }

        try {
            await fetch("http://localhost:8080/api/covoiturage/presence", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload), // envoi d'un tableau complet
            });
            setShowModal(false);
            setSelectedAllerIds([]);
            setSelectedRetourIds([]);
        } catch (err) {
            console.error("Erreur savePresence:", err);
        }
    };


    return (
        <div className="flex flex-col items-center">
            <Calendar onClickDay={handleDayClick} />

            {showModal && (
                <div className="fixed inset-0 bg-black bg-opacity-40 flex justify-center items-start pt-20 z-50">
                    <div className="bg-white p-6 rounded-lg shadow-lg w-11/12 max-w-3xl">
                        <h2 className="text-lg font-semibold mb-4">
                            Présence du {date?.toLocaleDateString()}
                        </h2>

                        {/* Choix des trajets pour Aller/Retour */}
                        <div className="mb-4 flex gap-4">
                            <div>
                                <label className="block text-sm">Trajet — Aller</label>
                                {trajets.length > 0 && (
                                    <select
                                        value={trajetAllerId ?? trajets[1].id}
                                        onChange={(e) => setTrajetAllerId(Number(e.target.value))}
                                        className="border p-1 rounded"
                                    >
                                        {trajets.map((t) => (
                                            <option key={t.id} value={t.id}>
                                                {t.lieuDepart} - {t.lieuArrivee}
                                            </option>
                                        ))}
                                    </select>
                                )}
                            </div>

                            <div>
                                <label className="block text-sm">Trajet — Retour</label>
                                <select
                                    value={trajetRetourId ?? ""}
                                    onChange={(e) => setTrajetRetourId(Number(e.target.value) || null)}
                                    className="border p-1 rounded"
                                >
                                    {trajets.map((t) => (
                                        <option key={t.id} value={t.id}>
                                            {t.lieuDepart ?? "?"} → {t.lieuArrivee ?? "?"} ({t.id})
                                        </option>
                                    ))}
                                </select>
                            </div>
                        </div>

                        <div className="grid grid-cols-2 gap-6">
                            <div>
                                <h3 className="font-medium mb-2">Aller</h3>
                                <div className="space-y-2 max-h-40 overflow-y-auto">
                                    {covoitureurs.map((c) => (
                                        <label key={c.id} className="flex items-center gap-2">
                                            <input
                                                type="checkbox"
                                                checked={selectedAllerIds.includes(c.id)}
                                                onChange={() => toggleAller(c.id)}
                                            />
                                            <span>{c.nom}</span>
                                        </label>
                                    ))}
                                </div>
                            </div>

                            <div>
                                <h3 className="font-medium mb-2">Retour</h3>
                                <div className="space-y-2 max-h-40 overflow-y-auto">
                                    {covoitureurs.map((c) => (
                                        <label key={c.id} className="flex items-center gap-2">
                                            <input
                                                type="checkbox"
                                                checked={selectedRetourIds.includes(c.id)}
                                                onChange={() => toggleRetour(c.id)}
                                            />
                                            <span>{c.nom}</span>
                                        </label>
                                    ))}
                                </div>
                            </div>
                        </div>

                        <div className="mt-4 flex justify-end gap-2">
                            <button
                                onClick={() => setShowModal(false)}
                                className="px-3 py-1 bg-gray-300 rounded hover:bg-gray-400"
                            >
                                Annuler
                            </button>
                            <button
                                onClick={savePresence}
                                className="px-3 py-1 bg-blue-600 text-white rounded hover:bg-blue-700"
                            >
                                Valider
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
}
