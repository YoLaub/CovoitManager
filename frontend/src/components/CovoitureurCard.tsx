import React from "react";
import { resetSoldeCovoitureur} from "../api/api.ts";

interface CovoitureurCardProps {
    id: number;
    nom: string;
    email: string;
    solde: number;
    onResetSolde: (id: number) => void;
}


const CovoitureurCard: React.FC<CovoitureurCardProps> = ({id, nom, email, solde, onResetSolde }) => {

    const handleSolder = async () => {
        try {
            await resetSoldeCovoitureur({id});
            onResetSolde(id);

        } catch (err) {
            console.error("Erreur savePresence:", err);
        }
    }

    return (
        <div className="bg-white py-16 ">

                        <div className="grid grid-cols-3 gap-2  items-center border-2 border-gray-200 rounded-lg p-6 shadow-md">
                            <div className="flex flex-col items-center justify-center">
                                <img
                                    src="https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80"
                                    alt="" className=" size-16 rounded-full outline-1 -outline-offset-1 outline-black/5"/>
                            </div>

                            <div>
                                <h3 className="text-base/7 font-semibold tracking-tight text-gray-900">{nom}</h3>
                                <p className="text-sm/6 font-semibold text-gray-900">{email}</p>
                                <p className="text-sm/6 font-semibold text-gray-900">Solde: {solde.toFixed(2)} €</p>
                            </div>
                            <div className="items-center justify-end flex flex-col gap-2">
                                <button className="text-sm/6 font-semibold bg-blue-500 rounded-2xl text-white px-4 py-2">
                                    <a onClick={handleSolder}>Solder</a>
                                </button>
                                <button className="text-sm/6 font-semibold bg-emerald-400 rounded-2xl text-white px-5 py-2">
                                    <a href={"handleSend"}>Email</a>
                                </button>
                            </div>
                        </div>
        </div>
    );
};

export default CovoitureurCard;
