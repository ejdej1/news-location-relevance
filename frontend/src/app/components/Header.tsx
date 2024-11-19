import { useState } from "react";
import { useToast } from "./use-toast";

interface HeaderProps {
    handleFetchNews: (cityName: string, stateName: string) => Promise<void>;
}

function Header({ handleFetchNews }: HeaderProps) {

    const { toast } = useToast()

    const [city, setCity] = useState<string>(""); 
    const [state, setState] = useState<string>("");  

    const handleClick = () => {
        if (city && state) {
          handleFetchNews(city, state); 
        } else {
            toast({
                title: "You need to provide both City name and State name",
                description: "",
              })
        }
    };

    return(
        <div className="w-full h-[100px] bg-blue-300 p-[35px]">
            <section className="w-fit m-auto">
            <input 
                className="py-2.5 px-2 mx-3 rounded-md w-[250px]" 
                type="text" 
                placeholder="Enter city" 
                value={city} 
                onChange={(e) => setCity(e.target.value)} 
            />

            <input 
                className="py-2.5 px-2 mx-3 rounded-md w-[250px]"
                type="text" 
                placeholder="Enter state" 
                value={state} 
                onChange={(e) => setState(e.target.value)} 
            />

            <button  className="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800" 
                    onClick={handleClick}>
                Search News
            </button>
            </section>
        </div>
    );
}

export default Header;
