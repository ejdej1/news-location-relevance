"use client"

import { useState, useEffect } from "react";
import { News } from "./components/types";
import NewsCard from "./components/NewsCard";
import Header from "./components/Header";
import { useToast } from "./components/use-toast";

export default function Home() {

  const [newsData, setNewsData] = useState<News[] | null>(null); 
  const [isLoading, setIsLoading] = useState<boolean>(false); 
  const [error, setError] = useState<string | null>(null);
  const { toast } = useToast()

  useEffect(() => {
    const fetchGlobalNewsStart = async () => {
      setIsLoading(true);  
      setError(null); 
      try {
        const data = await fetchGlobalNews();
        setNewsData(data);
      } catch (err: any) {
        setError(err.message);  
      } finally {
        setIsLoading(false); 
      }
    };

    fetchGlobalNewsStart(); 

  }, []); 

  const checkCityExists = async (cityName: string, stateName: string): Promise<boolean> => {
    try {
      const response = await fetch(`http://ec2-54-92-155-110.compute-1.amazonaws.com:8080/cities/exist?cityName=${cityName}&stateName=${stateName}`);
      if (!response.ok) {
        throw new Error('City existence check failed');
      }
      const exists = await response.json();
      return exists; 
    } catch (error) {
      throw new Error('Error checking city existence');
    }
  };

  const fetchByCityName = async (cityName: string, stateName: string): Promise<News[]> => {

    const cityExists = await checkCityExists(cityName, stateName);

    if (!cityExists) {
      toast({
        title: "City is not present in database",
        description: "",
      })
      
      throw Error("Location is not present in database")
    } else {
      const response = await fetch(`http://ec2-54-92-155-110.compute-1.amazonaws.com:8080/news/city?cityName=${cityName}&stateName=${stateName}`);
    if (!response.ok) {
      throw new Error("Failed to fetch news");
    }
    return response.json(); 
    }
  };

  const fetchGlobalNews = async (): Promise<News[]> => {
    const response = await fetch("http://ec2-54-92-155-110.compute-1.amazonaws.com:8080/news/global");
    if (!response.ok) {
      throw new Error("Failed to fetch news");
    }
    const data: News[] = await response.json();
    return data; 
  }

  const handleFetchNews = async (cityName: string, stateName: string) => {
    setIsLoading(true); 
    setError(null);

    try {
        const trimmedCityName = cityName.trim();
        const trimmedStateName = stateName.trim();

        const data = await fetchByCityName(trimmedCityName, trimmedStateName); 
        const data_global = await fetchGlobalNews();
        setNewsData([...data, ...data_global]);
    } catch (err: any) {
        setError(err.message); 
    } finally {
        setIsLoading(false);
    }
};


  return (
    <div className="bg-white">
      <Header handleFetchNews={handleFetchNews} />
      {!isLoading  && newsData != null ? 
      <div>
        {newsData.length == 0 ? <>No news in database </>: <></>}
        {newsData.map((newsItem: News, index: number) => ( 
        <NewsCard key={index} news={newsItem}/>))}
      </div> 
        : <div className="w-fit m-auto mt-[100px]">
        <div role="status">
            <svg aria-hidden="true" className="w-8 h-8 text-gray-200 animate-spin dark:text-gray-600 fill-blue-600" viewBox="0 0 100 101" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M100 50.5908C100 78.2051 77.6142 100.591 50 100.591C22.3858 100.591 0 78.2051 0 50.5908C0 22.9766 22.3858 0.59082 50 0.59082C77.6142 0.59082 100 22.9766 100 50.5908ZM9.08144 50.5908C9.08144 73.1895 27.4013 91.5094 50 91.5094C72.5987 91.5094 90.9186 73.1895 90.9186 50.5908C90.9186 27.9921 72.5987 9.67226 50 9.67226C27.4013 9.67226 9.08144 27.9921 9.08144 50.5908Z" fill="currentColor"/>
                <path d="M93.9676 39.0409C96.393 38.4038 97.8624 35.9116 97.0079 33.5539C95.2932 28.8227 92.871 24.3692 89.8167 20.348C85.8452 15.1192 80.8826 10.7238 75.2124 7.41289C69.5422 4.10194 63.2754 1.94025 56.7698 1.05124C51.7666 0.367541 46.6976 0.446843 41.7345 1.27873C39.2613 1.69328 37.813 4.19778 38.4501 6.62326C39.0873 9.04874 41.5694 10.4717 44.0505 10.1071C47.8511 9.54855 51.7191 9.52689 55.5402 10.0491C60.8642 10.7766 65.9928 12.5457 70.6331 15.2552C75.2735 17.9648 79.3347 21.5619 82.5849 25.841C84.9175 28.9121 86.7997 32.2913 88.1811 35.8758C89.083 38.2158 91.5421 39.6781 93.9676 39.0409Z" fill="currentFill"/>
            </svg>
            <span className="sr-only">Loading...</span>
        </div>
        </div>}
    </div>
  );
}
