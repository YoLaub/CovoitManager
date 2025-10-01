import React from "react";

interface CovoitureurCardProps {
    id: number;
    nom: string;
    email: string;
    solde: number;
}

const CovoitureurCard: React.FC<CovoitureurCardProps> = ({ nom, email, solde }) => {
    return (
        <div className="bg-white py-16 ">
            <div className="mx-auto grid max-w-7xl gap-20 px-6 lg:px-8 xl:grid-cols-3">

                <ul role="list" className="grid gap-x-8 gap-y-6 sm:grid-cols-1 sm:gap-y-16 xl:col-span-2">
                    <li>
                        <div className="flex gap-7 items-center border-2 border-gray-200 rounded-lg p-6 shadow-md">
                            <img src="https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80" alt="" className="size-16 rounded-full outline-1 -outline-offset-1 outline-black/5" />
                            <div>
                                <h3 className="text-base/7 font-semibold tracking-tight text-gray-900">{nom}</h3>
                                <p className="text-sm/6 font-semibold text-gray-900">Email: {email}</p>
                                <p className="text-sm/6 font-semibold text-gray-900">Solde: {solde.toFixed(2)} €</p>
                            </div>
                            <div className="flex gap-2">
                                <button className="text-sm/6 font-semibold text-gray-900">
                                    <a href={"solder"}>Solder</a>
                                </button>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
);
};

export default CovoitureurCard;
