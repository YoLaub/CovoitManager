import React from "react";

interface CovoitureurCardProps {
    id: number;
    nom: string;
    email: string;
    solde: number;
}

const CovoitureurCard: React.FC<CovoitureurCardProps> = ({ id, nom, email, solde }) => {
    return (
        <div className="card">
            <h3>{nom}  - {id}</h3>
            <p>Email: {email}</p>
            <p>Solde: {solde.toFixed(2)} €</p>
        </div>
    );
};

export default CovoitureurCard;
