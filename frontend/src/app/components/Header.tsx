import { useState } from "react";
import { useToast } from "./use-toast";

interface HeaderProps {
    handleFetchNews: (cityName: string, stateName: string) => Promise<void>;
}

function Header({ handleFetchNews }: HeaderProps) {

    const { toast } = useToast();

    const [city, setCity] = useState<string>(""); 
    const [state, setState] = useState<string>("");  

    const handleClick = () => {
        if (city && state) {
            handleFetchNews(city, state); 
        } else {
            toast({
                title: "You need to provide both City name and State name",
                description: "",
            });
        }
    };

    return (
        <div className="w-full bg-blue-300 p-4 sm:p-6">
            <section className="max-w-lg mx-auto flex flex-col sm:flex-row sm:items-center gap-3">
                <input 
                    className="flex-1 py-2 px-3 rounded-md w-full sm:w-auto border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    type="text" 
                    placeholder="Enter city" 
                    value={city} 
                    onChange={(e) => setCity(e.target.value)} 
                />

                <input 
                    className="flex-1 py-2 px-3 rounded-md w-full sm:w-auto border border-gray-300 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    type="text" 
                    placeholder="Enter state" 
                    value={state} 
                    onChange={(e) => setState(e.target.value)} 
                />

                <button 
                    className="w-full sm:w-auto text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-4 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800 whitespace-nowrap"
                    onClick={handleClick}
                >
                    Search News
                </button>

            </section>
        </div>
    );
}

export default Header;

