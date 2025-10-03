export { getColorFromId, formatDate };

function getColorFromId(id: number) {
    return `hsl(${id * 40 % 360}, 70%, 60%)`;
}

const formatDate = (d: Date): string => {
    const year = d.getFullYear();
    const month = String(d.getMonth() + 1).padStart(2, "0"); // ajoute un zéro devant si besoin
    const day = String(d.getDate()).padStart(2, "0");
    return `${year}-${month}-${day}`;
};
