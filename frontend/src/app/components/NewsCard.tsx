import { News } from "./types"

interface NewsCardProps {
    news: News;
}

function NewsCard({ news }: NewsCardProps) {
    const handleRedirect = () => {
      window.location.href = news.sourceUrl;
    };
  
    const getFormattedDate = (dateString: string) => {
      const date = new Date(dateString);
      return isNaN(date.getTime()) ? "Unknown" : date.toLocaleDateString("en-GB");
    };
  
    const formattedDate = getFormattedDate(news.date);
  
    const fallbackImageUrl = "https://via.placeholder.com/300?text=Image+Unavailable";
  
    return (
      <div className="w-full my-10 max-w-[1000px] shadow-xl p-10 rounded-lg m-auto">
        <h1 className="text-3xl mb-2">{news.title}</h1>
        {news.classification === "Global" ? (
          <p className="bg-green-200 rounded-lg px-5 border-2 border-green-300 w-fit my-5">{news.classification}</p>
        ) : (
          <p className="bg-orange-200 rounded-lg px-5 border-2 border-orange-300 w-fit my-5">{news.classification}</p>
        )}
        <img
          className="m-auto" 
          src={news.imageUrl}
          alt={news.title}
          onError={(e) => {
            e.currentTarget.src = fallbackImageUrl;
          }}
        />
        <p className="my-10">{news.content}</p>
        <div>
          <p>Date: {formattedDate}</p>
          <p>Author: {news.author}</p>
        </div>
        <button
          className="text-white bg-blue-700 my-5 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2 dark:bg-blue-600 dark:hover:bg-blue-700 focus:outline-none dark:focus:ring-blue-800"
          onClick={handleRedirect}
        >
          See details
        </button>
      </div>
    );
  }
  
  export default NewsCard;
  
  

  