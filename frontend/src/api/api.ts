const API_BASE = "http://localhost:8080/api/covoiturage";

export async function getCovoitureurs() {
    const res = await fetch(`${API_BASE}/covoitureur`,{
        method: "GET",
        headers: {"Content-Type": "application/json"}
});
    return res.json();
}

export async function addCovoitureur(data: {nom: string, email: string, solde: number}) {
    const res = await fetch(`${API_BASE}/covoitureur`, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(data)
    });
    return res.json();
}

// Ajoute d'autres fonctions pour trajets, présences, etc.
