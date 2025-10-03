import React, { useEffect, useState } from "react";
import { getCovoitureurs } from "../api/api";
import CovoitureurCard from "../components/CovoitureurCard";

interface Covoitureur {
    id: number;
    nom: string;
    email: string;
    solde: number;
}

const Dashboard: React.FC = () => {
    const [covoitureurs, setCovoitureurs] = useState<Covoitureur[]>([]);

    useEffect(() => {
        async function fetchData() {
            const data = await getCovoitureurs();
            setCovoitureurs(data);
        }
        fetchData();
    }, []);

    return (
        <div>
            <h2 className="text-2xl font-semibold tracking-tight text-gray-900">Liste des covoitureurs</h2>
            <div>
                {covoitureurs.map(c => (
                    <CovoitureurCard
                        key={c.id}
                        id={c.id}
                        nom={c.nom}
                        email={c.email}
                        solde={c.solde}
                        onResetSolde={(id) => {
                            setCovoitureurs(prev =>
                                prev.map(cv =>
                                    cv.id === id ? { ...cv, solde: 0 } : cv
                                )
                            );
                        }}
                    />
                ))}
            </div>
        </div>
    );
};

export default Dashboard;
