import React from "react";

interface TrajetCardProps {
    id: number;
    depart: string;
    destination: string;
    prix: number;
}

const TrajetCard: React.FC<TrajetCardProps> = ({ depart, destination, prix }) => {
    return (
        <div className="card">
            <h3>{depart} → {destination}</h3>
            <p>Prix : {prix.toFixed(2)} €</p>
        </div>
    );
};

export default TrajetCard;
