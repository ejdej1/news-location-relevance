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
      
      throw Error("City is not present in database")
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
      const data = await fetchByCityName(cityName, stateName); 
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
        : <></>}
    </div>
  );
}
